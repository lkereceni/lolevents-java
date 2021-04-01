package com.example.loleventspma.Fragments;

import android.os.Bundle;
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

import com.example.loleventspma.Adapters.TeamsAdapter;
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

public class TeamsFragment extends Fragment {

    ArrayList<Team> teams = new ArrayList<>();
    private String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
    private TeamsAdapter teamsAdapter;
    private RecyclerView teams_recyclerView;
    private GridLayoutManager gridLayoutManager;

    public TeamsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        teams_recyclerView = view.findViewById(R.id.teams_recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        teams_recyclerView.setLayoutManager(gridLayoutManager);
        teams_recyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);

        getTeams();

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
                //teamsAdapter.getFilter().filter(newText);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.pandascore.co/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);
                Call<List<Team>> call = requestInterface.getTeamsJsonFiltered(newText, token);

                call.enqueue(new Callback<List<Team>>() {
                    @Override
                    public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                        teams = new ArrayList<>(response.body());
                        teamsAdapter = new TeamsAdapter(teams, getContext());
                        teams_recyclerView.setAdapter(teamsAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Team>> call, Throwable t) {
                    }
                });
                return false;
            }
        });
    }

    private void getTeams() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Team>> call = requestInterface.getTeamsJson(500, token);

        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                teams = new ArrayList<>(response.body());
                teamsAdapter = new TeamsAdapter(teams, getContext());
                teams_recyclerView.setAdapter(teamsAdapter);
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
            }
        });
    }
}
