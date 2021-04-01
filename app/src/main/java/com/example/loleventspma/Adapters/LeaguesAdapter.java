package com.example.loleventspma.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loleventspma.Activities.SingleLeagueActivity;
import com.example.loleventspma.Classes.League;
import com.example.loleventspma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaguesAdapter extends RecyclerView.Adapter<LeaguesAdapter.ViewHolder> {
    private ArrayList<League> leagues;
    private Context context;

    public LeaguesAdapter(Context context, ArrayList<League> leagues) {
        this.leagues = leagues;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaguesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leagues_grid_list_item, viewGroup, false);
        return new LeaguesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaguesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.league_name.setText(leagues.get(i).getName());
        Picasso.get().load(leagues.get(i).getImageUrl()).resize(600, 600).onlyScaleDown().into(viewHolder.league_image);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleLeagueActivity.class);
            intent.putExtra("league_id", leagues.get(i).getId().toString());
            for(int j = 0; j < leagues.get(i).getSeries().size(); j++) {
                intent.putExtra("serie_id", leagues.get(i).getSeries().get(j).getSerieId());
            }
            intent.putExtra("name", leagues.get(i).getName());
            intent.putExtra("imageUrl", leagues.get(i).getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView league_image;
        private TextView league_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            league_image = itemView.findViewById(R.id.league_image);
            league_name = itemView.findViewById(R.id.league_name);

            itemView.setTag(itemView);
        }
    }
}
