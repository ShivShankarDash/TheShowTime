package com.example.shiv.theshowtime;

import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieCreditsResponse;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieDetails;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MoviesResponseAll;
import com.example.shiv.theshowtime.NetworkClasses.Movies.SimilarMoviesResponse;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShow;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowCreditsResponse;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowsResponseAll;
import com.example.shiv.theshowtime.NetworkClasses.YoutubeVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAndTVShowsApi {


    @GET("movie/now_playing")
    Call<MoviesResponseAll> getlatest(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/popular")
    Call<MoviesResponseAll> getPopular(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MoviesResponseAll> getUpcoming(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesResponseAll> getTopRated(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<YoutubeVideoResponse> getMovieTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<MovieCreditsResponse> getMovieCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/similar")
    Call<SimilarMoviesResponse> getSimilarMovies(@Path("id") Integer movieId, @Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/airing_today")
    Call<TVShowsResponseAll> getAiringTodayTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/on_the_air")
    Call<TVShowsResponseAll> getOnTheAirTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/popular")
    Call<TVShowsResponseAll> getPopularTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/top_rated")
    Call<TVShowsResponseAll> getTopRatedTVShows(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("tv/{id}")
    Call<TVShow> getTVShowDetails(@Path("id") long tvShowId, @Query("api_key") String apiKey);

    @GET("tv/{id}/videos")
    Call<YoutubeVideoResponse> getTVShowVideos(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    @GET("tv/{id}/credits")
    Call<TVShowCreditsResponse> getTVShowCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey);

    @GET("tv/{id}/similar")
    Call<TVShowsResponseAll> getSimilarTVShows(@Path("id") Integer movieId, @Query("api_key") String apiKey, @Query("page") Integer page);


}
