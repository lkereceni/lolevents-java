package com.example.loleventspma.Classes;

import android.graphics.Path;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.ArrayList;

public class Match {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("league_id")
    @Expose
    private Integer leagueId;

    @SerializedName("begin_at")
    @Expose
    private String beginAt;

    @SerializedName("match_type")
    @Expose
    private String matchType;

    @SerializedName("number_of_games")
    @Expose
    private String numberOfGames;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("winner_id")
    @Expose
    private String winnerId;

    @SerializedName("opponents")
    @Expose
    private ArrayList<Opponents> opponents;

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(String numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public ArrayList<Opponents> getOpponents() {
        return opponents;
    }

    public void setOpponents(ArrayList<Opponents> opponents) {
        this.opponents = opponents;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
