package com.example.shiv.theshowtime.NetworkClasses;

import com.google.gson.annotations.SerializedName;

public class Genres {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String genreName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Genres(Integer id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }
}
