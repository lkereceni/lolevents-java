package com.example.loleventspma.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SingleLeague {
    @SerializedName("league_id")
    @Expose
    private String leagueId;

    @SerializedName("serie_id")
    @Expose
    private String serieId;

    @SerializedName("matches")
    @Expose
    private ArrayList<Match> matches;

    @SerializedName("teams")
    @Expose
    private ArrayList<Team> teams;



    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getSerieId() {
        return serieId;
    }

    public void setSerieId(String serieId) {
        this.serieId = serieId;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
