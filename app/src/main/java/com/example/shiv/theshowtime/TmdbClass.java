package com.example.shiv.theshowtime;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TmdbClass {


@SerializedName("results")
ArrayList<MovieResults> movieResults;

    public TmdbClass(ArrayList<MovieResults> movieResults) {
        this.movieResults = movieResults;
    }

    public ArrayList<MovieResults> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(ArrayList<MovieResults> movieResults) {
        this.movieResults = movieResults;
    }
}
