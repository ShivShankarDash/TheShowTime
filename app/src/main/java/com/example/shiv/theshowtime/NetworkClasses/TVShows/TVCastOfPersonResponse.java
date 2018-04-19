package com.example.shiv.theshowtime.NetworkClasses.TVShows;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVCastOfPersonResponse {

    @SerializedName("cast")
    private List<TVCastOfPerson> casts;
    //crew missing
    @SerializedName("id")
    private Integer id;

    public TVCastOfPersonResponse(List<TVCastOfPerson> casts, Integer id) {
        this.casts = casts;
        this.id = id;
    }

    public List<TVCastOfPerson> getCasts() {
        return casts;
    }

    public void setCasts(List<TVCastOfPerson> casts) {
        this.casts = casts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
