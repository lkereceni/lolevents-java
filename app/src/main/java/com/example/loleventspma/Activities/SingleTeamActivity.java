package com.example.loleventspma.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.loleventspma.Adapters.MatchesAdapter;
import com.example.loleventspma.Adapters.PlayersAdapter;
import com.example.loleventspma.Classes.Match;
import com.example.loleventspma.Classes.Team;
import com.example.loleventspma.R;
import com.example.loleventspma.RequestInterface;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleTeamActivity extends AppCompatActivity {
    private Team oTeam = new Team();
    private ArrayList<Team> team = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();
    private PlayersAdapter playersAdapter;
    private MatchesAdapter matchesAdapter;

    private ImageView teamImage;
    private TextView teamName;
    private Spinner singleTeamSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_team);

        teamImage = findViewById(R.id.single_team_image);
        teamName = findViewById(R.id.single_team_name);
        singleTeamSpinner = findViewById(R.id.single_team_spinner);

        Intent intent = getIntent();
        String team_id = intent.getStringExtra("team_id");
        String team_image = intent.getStringExtra("image_url");
        String team_name = intent.getStringExtra("name");

        oTeam.setId(team_id);
        oTeam.setImageUrl(team_image);
        oTeam.setName(team_name);
        team.add(oTeam);

        Picasso.get().load(team.get(0).getImageUrl()).resize(400, 400).onlyScaleDown().into(teamImage);
        teamName.setText(team_name);

        String[] spinnerItems = new String[] {
                "Matches",
                "Players"
        };

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        singleTeamSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        singleTeamSpinner.setAdapter(spinnerArrayAdapter);

        singleTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    displayMatches();
                } else {
                    displayPlayers();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) { }
    }

    private void displayMatches() {
        RecyclerView matches_recyclerView;
        GridLayoutManager gridLayoutManager;

        matches_recyclerView = findViewById(R.id.single_team_list);
        gridLayoutManager = new GridLayoutManager(SingleTeamActivity.this, 1, LinearLayoutManager.VERTICAL, false);
        matches_recyclerView.setLayoutManager(gridLayoutManager);
        matches_recyclerView.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
        String team_id = getIntent().getStringExtra("team_id");

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Match>> call = requestInterface.getTeamMatchesJson(team_id, token);
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

    private void displayPlayers() {
        RecyclerView players_recyclerView;
        GridLayoutManager gridLayoutManager;

        players_recyclerView = findViewById(R.id.single_team_list);
        gridLayoutManager = new GridLayoutManager(SingleTeamActivity.this, 2, LinearLayoutManager.VERTICAL, false);
        players_recyclerView.setLayoutManager(gridLayoutManager);
        players_recyclerView.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
        String team_id = getIntent().getStringExtra("team_id");

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Team>> call = requestInterface.getTeamPlayersJson(team_id, 500, token);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                team = new ArrayList<>(response.body());
                if(team.get(0).getPlayers().size() == 0) {
                    playersAdapter = null;
                } else {
                    playersAdapter = new PlayersAdapter(team.get(0).getPlayers(), getApplicationContext());
                }
                players_recyclerView.setAdapter(playersAdapter);
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}
