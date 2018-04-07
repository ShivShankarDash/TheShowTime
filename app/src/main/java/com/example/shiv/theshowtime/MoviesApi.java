package com.example.shiv.theshowtime;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesApi {


   @GET("https://api.themoviedb.org/3/movie/now_playing?api_key=ee5c0dbbf22e66d8a4091557e20acabb&language=en-US&page=1")
   Call<TmdbClass> getlatest();



}
