package com.example.loleventspma.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loleventspma.Classes.Match;
import com.example.loleventspma.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private ArrayList<Match> matches;
    private Context context;

    public MatchesAdapter(ArrayList<Match> matches, Context context) {
        this.matches = matches;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.matches_grid_list_item, viewGroup, false);
        return new MatchesAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder viewHolder, int i) {
        if (matches.get(i).getOpponents().size() != 2) {
            viewHolder.team1_logo.getDrawable();
            viewHolder.team2_logo.getDrawable();
            viewHolder.team1_name.setText("TBD");
            viewHolder.team2_name.setText("TBD");
        } else {
            Picasso.get().load(matches.get(i).getOpponents().get(0).getOpponent().getImageUrl()).into(viewHolder.team1_logo);
            Picasso.get().load(matches.get(i).getOpponents().get(1).getOpponent().getImageUrl()).into(viewHolder.team2_logo);
            viewHolder.team1_name.setText(matches.get(i).getOpponents().get(0).getOpponent().getAcronym());
            viewHolder.team2_name.setText(matches.get(i).getOpponents().get(1).getOpponent().getAcronym());
        }
        if (matches.get(i).getResults().size() != 2) {
            viewHolder.team1_score.setText(" ");
            viewHolder.team2_score.setText(" ");
        } else {
            viewHolder.team1_score.setText(matches.get(i).getResults().get(0).getScore());
            viewHolder.team2_score.setText(matches.get(i).getResults().get(1).getScore());
        }
        viewHolder.number_of_games.setText("Best of " + matches.get(i).getNumberOfGames());
        String beginAt = matches.get(i).getBeginAt();
        if(beginAt == null) {
            viewHolder.date.setText("Undefined");
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date d = null;
            try {
                d = sdf.parse(beginAt);
            } catch (ParseException e) {
                Log.v("Exception", e.getLocalizedMessage());
            }
            sdf.applyPattern("dd.MM.yyyy. HH:mm");
            viewHolder.date.setText(sdf.format(d));
        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView team1_logo, team2_logo;
        private TextView team1_name, team2_name;
        private TextView team1_score, team2_score;
        private TextView number_of_games;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team1_logo = itemView.findViewById(R.id.team1_logo);
            team2_logo = itemView.findViewById(R.id.team2_logo);
            team1_name = itemView.findViewById(R.id.team1_name);
            team2_name = itemView.findViewById(R.id.team2_name);
            team1_score = itemView.findViewById(R.id.team1_score);
            team2_score = itemView.findViewById(R.id.team2_score);
            number_of_games = itemView.findViewById(R.id.number_of_games_text);
            date = itemView.findViewById(R.id.match_date_text);

            itemView.setTag(itemView);
        }
    }
}
