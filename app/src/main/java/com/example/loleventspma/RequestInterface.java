package com.example.loleventspma;

import com.example.loleventspma.Classes.League;
import com.example.loleventspma.Classes.Match;
import com.example.loleventspma.Classes.Player;
import com.example.loleventspma.Classes.SingleLeague;
import com.example.loleventspma.Classes.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("lol/leagues")
    Call<List<League>> getLeaguesJson(
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/leagues")
    Call<List<League>> getLeaguesJsonFiltered(
            @Query("search[name]") String leagueName,
            @Query("token") String token);

    @GET("lol/matches/past")
    Call<List<Match>> getPastMatchesJson(
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/matches/upcoming")
    Call<List<Match>> getUpcomingMatchesJson(
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/matches")
    Call<List<Match>> getSingleLeagueMatchesJson(
            @Query("filter[league_id]") String league_id,
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/teams")
    Call<List<Team>> getTeamsJson(
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/teams")
    Call<List<Team>> getTeamsJsonFiltered(
            @Query("search[name]") String teamName,
            @Query("token") String token);

    @GET("lol/teams")
    Call<List<Team>> getTeamPlayersJson(
            @Query("filter[id]") String team_id,
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("teams/{id}/matches")
    Call<List<Match>> getTeamMatchesJson(
            @Path("id") String id,
            @Query("token") String token);

    @GET("lol/tournaments")
    Call<List<SingleLeague>> getTournamentJson(
            @Query("filter[serie_id]") String serie_id,
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/players")
    Call<List<Player>> getPlayersJson(
            @Query("per_page") Integer perPage,
            @Query("token") String token);

    @GET("lol/players")
    Call<List<Player>> getPlayersJsonFiltered(
            @Query("search[name]") String playerName,
            @Query("token") String token);

    @GET("lol/players")
    Call<List<Player>> getSinglePlayerJson(
            @Query("filter[id]") String player_id,
            @Query("token") String token);
}
