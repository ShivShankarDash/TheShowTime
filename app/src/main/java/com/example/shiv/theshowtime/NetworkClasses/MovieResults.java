package com.example.shiv.theshowtime.NetworkClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResults {

    @SerializedName("id")
    private long id;
    @SerializedName("vote_count")
    private int votes;
    @SerializedName("vote_average")
    private double votesAvg;
    @SerializedName("title")
    private String title;
    @SerializedName("original_language")
    private String language;
    @SerializedName("original_title")
    private String originTitle;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public MovieResults(long id, int votes, double votesAvg, String title, String language, String originTitle, ArrayList<Integer> genreIds, String backdropPath, String posterPath, String overview, String releaseDate) {
        this.id = id;
        this.votes = votes;
        this.votesAvg = votesAvg;
        this.title = title;
        this.language = language;
        this.originTitle = originTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getVotesAvg() {
        return votesAvg;
    }

    public void setVotesAvg(double votesAvg) {
        this.votesAvg = votesAvg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
