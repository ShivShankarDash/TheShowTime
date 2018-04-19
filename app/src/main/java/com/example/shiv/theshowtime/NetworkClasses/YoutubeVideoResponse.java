package com.example.shiv.theshowtime.NetworkClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoutubeVideoResponse {

    @SerializedName("results")
    ArrayList<YoutubeVideo> videoresults;

    public ArrayList<YoutubeVideo> getVideoresults() {
        return videoresults;
    }

    public void setVideoresults(ArrayList<YoutubeVideo> videoresults) {
        this.videoresults = videoresults;
    }
}
