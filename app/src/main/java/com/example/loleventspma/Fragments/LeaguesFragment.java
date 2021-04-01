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

import com.example.loleventspma.Adapters.LeaguesAdapter;
import com.example.loleventspma.Classes.League;
import com.example.loleventspma.R;
import com.example.loleventspma.RequestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaguesFragment extends Fragment {

    ArrayList<League> leagues = new ArrayList<>();
    private String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
    private RecyclerView leagues_recyclerView;
    private LeaguesAdapter leaguesAdapter;
    private GridLayoutManager gridLayoutManager;

    public LeaguesFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leagues, container, false);

        leagues_recyclerView = view.findViewById(R.id.leagues_recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        leagues_recyclerView.setLayoutManager(gridLayoutManager);
        leagues_recyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);

        getLeagues();

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
                //leaguesAdapter.getFilter().filter(newText);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.pandascore.co/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);
                Call<List<League>> call = requestInterface.getLeaguesJsonFiltered(newText, token);
                call.enqueue(new Callback<List<League>>() {
                    @Override
                    public void onResponse(Call<List<League>> call, Response<List<League>> response) {
                        leagues = new ArrayList<>(response.body());
                        leaguesAdapter = new LeaguesAdapter(getActivity(), leagues);
                        leagues_recyclerView.setAdapter(leaguesAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<League>> call, Throwable t) { }
                });
                return false;
            }
        });
    }

    private void getLeagues() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<League>> call = requestInterface.getLeaguesJson(100, token);
        call.enqueue(new Callback<List<League>>() {
            @Override
            public void onResponse(Call<List<League>> call, Response<List<League>> response) {
                leagues = new ArrayList<>(response.body());
                leaguesAdapter = new LeaguesAdapter(getActivity(), leagues);
                leagues_recyclerView.setAdapter(leaguesAdapter);
            }
            @Override
            public void onFailure(Call<List<League>> call, Throwable t) { }
        });
    }
}
