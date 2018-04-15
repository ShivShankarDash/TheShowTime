package com.example.shiv.theshowtime;

import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.MovieCreditsResponse;
import com.example.shiv.theshowtime.NetworkClasses.MovieDetails;
import com.example.shiv.theshowtime.NetworkClasses.MoviesResponseAll;
import com.example.shiv.theshowtime.NetworkClasses.YoutubeVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {


   @GET("movie/now_playing")
   Call<MoviesResponseAll> getlatest(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

   @GET("movie/popular")
   Call<MoviesResponseAll> getPopular(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

@GET("movie/upcoming")
   Call<MoviesResponseAll> getUpcoming(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);
@GET("movie/top_rated")
   Call<MoviesResponseAll> getTopRated(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);
  @GET("movie/{id}")
  Call<MovieDetails> getMovieDetails(@Path("id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<YoutubeVideoResponse> getMovieTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<MovieCreditsResponse> getMovieCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey);

}
