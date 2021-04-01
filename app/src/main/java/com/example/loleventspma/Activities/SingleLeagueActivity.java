package com.example.loleventspma.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loleventspma.Adapters.MatchesAdapter;
import com.example.loleventspma.Adapters.TeamsAdapter;
import com.example.loleventspma.Classes.League;
import com.example.loleventspma.Classes.Match;
import com.example.loleventspma.Classes.SingleLeague;
import com.example.loleventspma.Classes.Team;
import com.example.loleventspma.R;
import com.example.loleventspma.RequestInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleLeagueActivity extends AppCompatActivity {
    SingleLeague oSingleLeague = new SingleLeague();
    League oLeague = new League();
    Match oMatch = new Match();
    Team oTeam = new Team();

    private ArrayList<SingleLeague> singleLeague = new ArrayList<>();
    private ArrayList<League> league = new ArrayList<>();
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();

    private TeamsAdapter teamsAdapter;
    private MatchesAdapter matchesAdapter;

    private ImageView leagueImage;
    private TextView leagueName;
    private  Spinner singleLeagueSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_league);

        singleLeagueSpinner = findViewById(R.id.single_league_spinner);
        leagueImage = findViewById(R.id.single_league_image);
        leagueName = findViewById(R.id.single_league_name);

        Intent intent = getIntent();
        String league_id = intent.getStringExtra("league_id");
        String serie_id = intent.getStringExtra("serie_id");
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("imageUrl");

        oLeague.setName(name);
        oLeague.setImageUrl(imageUrl);
        league.add(oLeague);

        Picasso.get().load(league.get(0).getImageUrl()).resize(600, 600).onlyScaleDown().into(leagueImage);
        leagueName.setText(league.get(0).getName());

        String[] spinnerItems = new String[] {
                "Matches",
                "Teams"
        };

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        singleLeagueSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        singleLeagueSpinner.setAdapter(spinnerArrayAdapter);

        singleLeagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    displayMatches();
                } else {
                    displayTeams();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
        }
    }

    private void displayMatches() {
        RecyclerView matches_recyclerView;
        GridLayoutManager gridLayoutManager;

        matches_recyclerView = findViewById(R.id.single_league_list);
        gridLayoutManager = new GridLayoutManager(SingleLeagueActivity.this, 1, LinearLayoutManager.VERTICAL, false);
        matches_recyclerView.setLayoutManager(gridLayoutManager);
        matches_recyclerView.setHasFixedSize(true);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
        String league_id = getIntent().getStringExtra("league_id");

        Call<List<Match>> call = requestInterface.getSingleLeagueMatchesJson(league_id, 500, token);
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                matches = new ArrayList<>(response.body());
                matchesAdapter = new MatchesAdapter(matches, getApplicationContext());
                matches_recyclerView.setAdapter(matchesAdapter);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void displayTeams() {
        RecyclerView teams_recyclerView;
        GridLayoutManager gridLayoutManager;

        teams_recyclerView = findViewById(R.id.single_league_list);
        gridLayoutManager = new GridLayoutManager(SingleLeagueActivity.this, 2, LinearLayoutManager.VERTICAL, false);
        teams_recyclerView.setLayoutManager(gridLayoutManager);
        teams_recyclerView.setHasFixedSize(true);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
        String serie_id = getIntent().getStringExtra("serie_id");

        Call<List<SingleLeague>> call = requestInterface.getTournamentJson(serie_id, 500, token);
        call.enqueue(new Callback<List<SingleLeague>>() {
            @Override
            public void onResponse(Call<List<SingleLeague>> call, Response<List<SingleLeague>> response) {
                singleLeague = new ArrayList<>(response.body());
                teamsAdapter = new TeamsAdapter(singleLeague.get(0).getTeams(), getApplicationContext());
                teams_recyclerView.setAdapter(teamsAdapter);
            }

            @Override
            public void onFailure(Call<List<SingleLeague>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}
