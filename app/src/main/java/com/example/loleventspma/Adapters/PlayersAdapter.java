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

import com.example.loleventspma.Activities.SinglePlayerActivity;
import com.example.loleventspma.Classes.Player;
import com.example.loleventspma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private ArrayList<Player> players;
    private Context context;

    public PlayersAdapter(ArrayList<Player> players, Context context) {
        this.players = players;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.players_grid_list_item, viewGroup, false);
        return new PlayersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.player_name.setText(players.get(i).getSummonerName());
        if(players.get(i).getImageUrl() == null) {
            viewHolder.player_image.getDrawable();
        } else {
            Picasso.get().load(players.get(i).getImageUrl()).into(viewHolder.player_image);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SinglePlayerActivity.class);
            intent.putExtra("player_id", players.get(i).getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView player_image;
        private TextView player_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            player_image = itemView.findViewById(R.id.player_image);
            player_name = itemView.findViewById(R.id.single_player_name);
        }
    }
}
