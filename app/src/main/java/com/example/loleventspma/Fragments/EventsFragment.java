package com.example.loleventspma.Fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loleventspma.Adapters.MatchesAdapter;
import com.example.loleventspma.Classes.Match;
import com.example.loleventspma.R;
import com.example.loleventspma.RequestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsFragment extends Fragment {

    ArrayList<Match> matches = new ArrayList<>();
    private String token = "5xdpBFcmaCOccUNyyqUG_RKJSmsTtLtocklUAA8ujukltrWTWTs";
    private RecyclerView matches_recyclerView;
    private MatchesAdapter matchesAdapter;
    private GridLayoutManager gridLayoutManager;
    private Spinner matchesSpinner;

    public EventsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        matches_recyclerView = view.findViewById(R.id.matches_recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        matches_recyclerView.setLayoutManager(gridLayoutManager);
        matches_recyclerView.setHasFixedSize(true);
        matchesSpinner = view.findViewById(R.id.matches_spinner);

        String[] spinnerItems = new String[] {
                "Past Matches",
                "Upcoming Matches"
        };

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        matchesSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        matchesSpinner.setAdapter(spinnerArrayAdapter);

        matchesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0) {
                        getPastMatches();
                    } else {
                        getUpcomingMatches();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void getPastMatches() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Match>> call = requestInterface.getPastMatchesJson(500, token);

        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                matches = new ArrayList<>(response.body());
                matchesAdapter = new MatchesAdapter(matches, getContext());
                matches_recyclerView.setAdapter(matchesAdapter);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void getUpcomingMatches() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pandascore.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<Match>> call = requestInterface.getUpcomingMatchesJson(500, token);

        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                matches = new ArrayList<>(response.body());
                matchesAdapter = new MatchesAdapter(matches, getContext());
                matches_recyclerView.setAdapter(matchesAdapter);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}
