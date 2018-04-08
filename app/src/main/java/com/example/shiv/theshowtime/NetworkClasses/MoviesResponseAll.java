package com.example.shiv.theshowtime.NetworkClasses;

import com.example.shiv.theshowtime.NetworkClasses.MovieResults;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesResponseAll {


@SerializedName("results")
public ArrayList<MovieResults> movieResults;
@SerializedName("page")
private int page;
@SerializedName("total_results")
private int totalpages;
@SerializedName("total_pages")
private int totalResults;

    public MoviesResponseAll(ArrayList<MovieResults> movieResults, int page, int totalpages, int totalResults) {
        this.movieResults = movieResults;
        this.page = page;
        this.totalpages = totalpages;
        this.totalResults = totalResults;
    }

    public MoviesResponseAll(int page, int totalpages, int totalResults) {
        this.page = page;
        this.totalpages = totalpages;
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public MoviesResponseAll(ArrayList<MovieResults> movieResults) {
        this.movieResults = movieResults;
    }

    public ArrayList<MovieResults> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(ArrayList<MovieResults> movieResults) {
        this.movieResults = movieResults;
    }
}
