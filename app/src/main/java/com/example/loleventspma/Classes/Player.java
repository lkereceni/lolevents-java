package com.example.loleventspma.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("name")
    @Expose
    private String summonerName;

    @SerializedName("nationality")
    @Expose
    private String nationality;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("current_team")
    @Expose
    private CurrentTeam currentTeam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public CurrentTeam getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(CurrentTeam currentTeam) {
        this.currentTeam = currentTeam;
    }
}
