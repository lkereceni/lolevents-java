package com.example.loleventspma.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loleventspma.Adapters.MatchesAdapter;
import com.example.loleventspma.Classes.Match;
import com.example.loleventspma.Classes.Player;
import com.example.loleventspma.Classes.Team;
import com.example.loleventspma.R;
import com.example.loleventspma.RequestInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SinglePlayerActivity extends AppCompatActivity {

    private Player oPlayer = new Player();
    private Team oTeam = new Team();
    private ArrayList<Player> player = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();
    private MatchesAdapter matchesAdapter;
    private Context context;

    private ImageView singlePlayerImage, singlePlayerTeamLogo, singlePLayerCountryFlag, roleImage;
    private TextView playerSummonerName, playerNameTop, singlePlayerName, singlePlayerCountry, singlePlayerTeamName, singlePlayerRole;

    private String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        singlePlayerImage = findViewById(R.id.single_player_image);
        singlePlayerTeamLogo = findViewById(R.id.single_player_team_logo);
        playerSummonerName = findViewById(R.id.player_summoner_name);
        playerNameTop = findViewById(R.id.player_name_top);
        singlePlayerName = findViewById(R.id.single_player_name);
        singlePlayerCountry = findViewById(R.id.single_player_country);
        singlePlayerTeamName = findViewById(R.id.single_player_team_name);
        singlePlayerRole = findViewById(R.id.single_player_role);
        singlePLayerCountryFlag = findViewById(R.id.single_player_country_flag);
        roleImage = findViewById(R.id.roleImage);

        Intent intent = getIntent();
        String player_id = intent.getStringExtra("player_id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Player>> call = requestInterface.getSinglePlayerJson(player_id, token);

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                assert response.body() != null;
                player = new ArrayList<>(response.body());

                if(player.get(0).getImageUrl() == null) {
                    singlePlayerImage.getDrawable();
                } else {
                    Picasso.get().load(player.get(0).getImageUrl()).into(singlePlayerImage);
                }
                if(player.get(0).getFirstName() == null || player.get(0).getLastName() == null) {
                    playerNameTop.setText(" ");
                } else {
                    playerNameTop.setText(player.get(0).getFirstName() + " " + player.get(0).getLastName());
                }
                playerSummonerName.setText(player.get(0).getSummonerName());
                singlePlayerName.setText(player.get(0).getFirstName() + " " + player.get(0).getLastName());
                if(player.get(0).getCurrentTeam() == null) {
                    singlePlayerTeamName.setText("Free Agent");
                } else {
                    singlePlayerTeamName.setText(player.get(0).getCurrentTeam().getName());
                }
                if(player.get(0).getCurrentTeam() == null) {
                    singlePlayerTeamLogo.getDrawable();
                } else {
                    Picasso.get().load(player.get(0).getCurrentTeam().getImageUrl()).resize(50, 50).onlyScaleDown().into(singlePlayerTeamLogo);
                }

                if(player.get(0).getNationality() == null) {
                    singlePLayerCountryFlag.setImageBitmap(null);
                    singlePlayerCountry.setText("Unknown");
                } else {
                    String natId = player.get(0).getNationality().toLowerCase();

                    int drawId = getResources().getIdentifier(natId, "drawable", getPackageName());
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawId);
                    singlePLayerCountryFlag.setImageBitmap(bitmap);

                    singlePlayerCountry.setText(getStringFromResourcesByName(natId));
                }
                String roleId = player.get(0).getRole().toLowerCase();
                int roleDrawId = getResources().getIdentifier(roleId, "drawable", getPackageName());
                Bitmap roleBitmap = BitmapFactory.decodeResource(getResources(), roleDrawId);
                roleImage.setImageBitmap(roleBitmap);
                singlePlayerRole.setText(getStringFromResourcesByName(roleId));

                if(player.get(0).getCurrentTeam() == null) {
                    getMatches(null);
                } else {
                    getMatches(player.get(0).getCurrentTeam().getId());
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });

        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) { }

    }

    private String getStringFromResourcesByName(String resourceName) {
        String packageName = getPackageName();
        int resourceId = getResources().getIdentifier(resourceName, "string", packageName);

        return getString(resourceId);
    }

    private void getMatches(String team_id) {
        RecyclerView matches_recyclerView;
        GridLayoutManager gridLayoutManager;

        matches_recyclerView = findViewById(R.id.single_player_matches_recycler_view);
        gridLayoutManager = new GridLayoutManager(SinglePlayerActivity.this, 1, LinearLayoutManager.VERTICAL, false);
        matches_recyclerView.setLayoutManager(gridLayoutManager);
        matches_recyclerView.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
}
