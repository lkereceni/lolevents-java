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

import com.example.loleventspma.Activities.SingleTeamActivity;
import com.example.loleventspma.Classes.Team;
import com.example.loleventspma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private ArrayList<Team> teams = new ArrayList<>();
    private Context context;

    public TeamsAdapter(ArrayList<Team> teams, Context context) {
        this.teams = teams;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teams_grid_list_item, viewGroup, false);
        return new TeamsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.team_name.setText(teams.get(i).getName());
        Picasso.get().load(teams.get(i).getImageUrl()).into(viewHolder.team_image);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleTeamActivity.class);
            intent.putExtra("team_id", teams.get(i).getId());
            intent.putExtra("name", teams.get(i).getName());
            intent.putExtra("image_url", teams.get(i).getImageUrl());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView team_image;
        private TextView team_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team_image = itemView.findViewById(R.id.team_image);
            team_name = itemView.findViewById(R.id.team_name);
        }
    }
}
