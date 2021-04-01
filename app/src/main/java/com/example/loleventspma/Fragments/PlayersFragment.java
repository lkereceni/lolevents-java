package com.example.loleventspma.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loleventspma.Adapters.PlayersAdapter;
import com.example.loleventspma.Adapters.TeamsAdapter;
import com.example.loleventspma.Classes.Player;
import com.example.loleventspma.Classes.Team;
import com.example.loleventspma.R;
import com.example.loleventspma.RequestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayersFragment extends Fragment {

    ArrayList<Player> players = new ArrayList<>();
    private String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
    private PlayersAdapter playersAdapter;
    private RecyclerView players_recyclerView;
    private GridLayoutManager gridLayoutManager;

    public PlayersFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        players_recyclerView = view.findViewById(R.id.players_recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        players_recyclerView.setLayoutManager(gridLayoutManager);
        players_recyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);

        getPlayers();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.pandascore.co/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);
                Call<List<Player>> call = requestInterface.getPlayersJsonFiltered(newText, token);

                call.enqueue(new Callback<List<Player>>() {
                    @Override
                    public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                        players = new ArrayList<>(response.body());
                        playersAdapter = new PlayersAdapter(players, getContext());
                        players_recyclerView.setAdapter(playersAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Player>> call, Throwable t) { }
                });

                return false;
            }
        });
    }

    private void getPlayers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Player>> call = requestInterface.getPlayersJson(500, token);

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                players = new ArrayList<>(response.body());
                playersAdapter = new PlayersAdapter(players, getContext());
                players_recyclerView.setAdapter(playersAdapter);
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}
