package com.example.shiv.theshowtime.Fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.shiv.theshowtime.Activities.ViewAllMoviesActivity;
import com.example.shiv.theshowtime.Adapters.MoviesAdapter;
import com.example.shiv.theshowtime.Adapters.SmallMoviesAdapter;
import com.example.shiv.theshowtime.MoviesAndTVShowsApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieResults;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MoviesResponseAll;
import com.example.shiv.theshowtime.R;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllMoviesFragment extends android.support.v4.app.Fragment{

    RecyclerView recyclerView,popularRecyclerView,upcomingrecyler,topratedRecyler;
    GoogleProgressBar googleProgressBar;
    MoviesAdapter moviesAdapter;
    SmallMoviesAdapter popularMoviesAdapter;
    MoviesAdapter upcomingMoviesAdapter;
    SmallMoviesAdapter topratedMoviesAdapter;

    ArrayList<MovieResults> movieResults=new ArrayList<>();
    ArrayList<MovieResults> popularMovieResults=new ArrayList<>();
    ArrayList<MovieResults> upcomingMoviesResults=new ArrayList<>();
    ArrayList<MovieResults> topratedMoviesResults=new ArrayList<>();

    Drawable progressDrawable;

    FrameLayout NowShowing,Popular,Upcoming,TopRated;
    TextView inTheatresViewAll,PopularViewAll,upcomingViewAll,topratedViewAll;



    private static final int CHROME_FLOATING_CIRCLES = 3;

    public AllMoviesFragment() {


    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.allmovies_fragment,container,false);

       NowShowing=view.findViewById(R.id.layout_now_showing);
       Popular=view.findViewById(R.id.layout_popular);
       TopRated=view.findViewById(R.id.layout_top_rated);
       Upcoming=view.findViewById(R.id.layout_upcoming);


       inTheatresViewAll=view.findViewById(R.id.text_view_view_all_now_showing);
       PopularViewAll=view.findViewById(R.id.text_view_view_all_popular);
       upcomingViewAll=view.findViewById(R.id.text_view_view_all_upcoming);
       topratedViewAll=view.findViewById(R.id.text_view_view_all_top_rated);




        recyclerView=view.findViewById(R.id.recyclerview);
        googleProgressBar=view.findViewById(R.id.googleprogressBar);
        popularRecyclerView=view.findViewById(R.id.popularrecycler);
        upcomingrecyler=view.findViewById(R.id.upcomingrecyler);
        topratedRecyler=view.findViewById(R.id.recycler_view_top_rated);

        moviesAdapter=new MoviesAdapter(movieResults,getContext());
        popularMoviesAdapter=new SmallMoviesAdapter(popularMovieResults,getContext());
        upcomingMoviesAdapter=new MoviesAdapter(upcomingMoviesResults,getContext());
        topratedMoviesAdapter=new SmallMoviesAdapter(topratedMoviesResults,getContext());

        progressDrawable = new ChromeFloatingCirclesDrawable.Builder(getContext())
                .colors(getProgressDrawableColors())
                .build();
        googleProgressBar.setIndeterminateDrawable(progressDrawable);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        SnapHelper snapHelper=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);


        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popularRecyclerView.setAdapter(popularMoviesAdapter);


        upcomingrecyler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        SnapHelper snapHelper1=new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(upcomingrecyler);
        upcomingrecyler.setItemAnimator(new DefaultItemAnimator());
        upcomingrecyler.setAdapter(upcomingMoviesAdapter);

        topratedRecyler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        topratedRecyler.setItemAnimator(new DefaultItemAnimator());
        topratedRecyler.setAdapter(topratedMoviesAdapter);


        inTheatresViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),ViewAllMoviesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE,Constants.NOW_SHOWING_MOVIES_TYPE);
                startActivity(intent);

            }
        });

        PopularViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), ViewAllMoviesActivity.class);

                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE,Constants.POPULAR_MOVIES_TYPE);

                startActivity(intent);


            }
        });

        upcomingViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),ViewAllMoviesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE,Constants.UPCOMING_MOVIES_TYPE);
                startActivity(intent);

            }
        });

        topratedViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),ViewAllMoviesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE,Constants.TOP_RATED_MOVIES_TYPE);
                startActivity(intent);

            }
        });

        fetchPopularMovieslist();
        fetchmovieslist();
        fetchUpcomingMovies();
        fetchTopRatedList();

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void fetchTopRatedList() {

        topratedRecyler.setVisibility(View.GONE);
        googleProgressBar.setVisibility(View.VISIBLE);

        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<MoviesResponseAll> call= moviesAndTVShowsApi.getTopRated(getString(R.string.Movie_DB_Api_key),"en-US",1);
        call.enqueue(new Callback<MoviesResponseAll>() {
            @Override
            public void onResponse(Call<MoviesResponseAll> call, Response<MoviesResponseAll> response) {

                MoviesResponseAll moviesResponseAll =response.body();
                if(moviesResponseAll.movieResults!=null){

                    topratedMoviesResults.clear();
                    topratedMoviesResults.addAll(moviesResponseAll.movieResults);
                    topratedMoviesAdapter.notifyDataSetChanged();

                }
                TopRated.setVisibility(View.VISIBLE);
                topratedRecyler.setVisibility(View.VISIBLE);
                googleProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MoviesResponseAll> call, Throwable t) {

            }
        });



    }

    private void fetchUpcomingMovies() {
        upcomingrecyler.setVisibility(View.GONE);
        googleProgressBar.setVisibility(View.VISIBLE);

        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<MoviesResponseAll> call= moviesAndTVShowsApi.getUpcoming(getString(R.string.Movie_DB_Api_key),"en-US",1);

        call.enqueue(new Callback<MoviesResponseAll>() {
            @Override
            public void onResponse(Call<MoviesResponseAll> call, Response<MoviesResponseAll> response) {

                MoviesResponseAll moviesResponseAll =response.body();

                if(moviesResponseAll!=null){

                    upcomingMoviesResults.clear();
                    upcomingMoviesResults.addAll(moviesResponseAll.movieResults);
                    upcomingMoviesAdapter.notifyDataSetChanged();

                }
                Upcoming.setVisibility(View.VISIBLE);
                upcomingrecyler.setVisibility(View.VISIBLE);
                googleProgressBar.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<MoviesResponseAll> call, Throwable t) {

            }
        });

    }

    private void fetchPopularMovieslist() {

        popularRecyclerView.setVisibility(View.GONE);
        googleProgressBar.setVisibility(View.VISIBLE);

        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
       Call<MoviesResponseAll> call= moviesAndTVShowsApi.getPopular(getString(R.string.Movie_DB_Api_key),"en-US",1);

      call.enqueue(new Callback<MoviesResponseAll>() {
          @Override
          public void onResponse(Call<MoviesResponseAll> call, Response<MoviesResponseAll> response) {

              MoviesResponseAll moviesResponseAll =response.body();

              if(moviesResponseAll.movieResults!=null){

                  popularMovieResults.clear();
                  popularMovieResults.addAll(moviesResponseAll.movieResults);
                  popularMoviesAdapter.notifyDataSetChanged();

              }

              Popular.setVisibility(View.VISIBLE);
              popularRecyclerView.setVisibility(View.VISIBLE);
              googleProgressBar.setVisibility(View.GONE);



          }

          @Override
          public void onFailure(Call<MoviesResponseAll> call, Throwable t) {

              Log.d("NOT FOUND",t.getLocalizedMessage());
//googleProgressBar.setVisibility(View.VISIBLE);


          }
      });





    }

    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        colors[0] = prefs.getInt(getString(R.string.firstcolor_pref_key),getResources().getColor(R.color.red));
        colors[1] = prefs.getInt(getString(R.string.secondcolor_pref_key),getResources().getColor(R.color.blue));
        colors[2] = prefs.getInt(getString(R.string.thirdcolor_pref_key),getResources().getColor(R.color.yellow));
        colors[3] = prefs.getInt(getString(R.string.fourthcolor_pref_key), getResources().getColor(R.color.green));
        return colors;
    }


    private void fetchmovieslist() {

        recyclerView.setVisibility(View.GONE);
        googleProgressBar.setVisibility(View.VISIBLE);

        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<MoviesResponseAll> call= moviesAndTVShowsApi.getlatest(getString(R.string.Movie_DB_Api_key),"en-US",1);

        call.enqueue(new Callback<MoviesResponseAll>() {
            @Override
            public void onResponse(Call<MoviesResponseAll> call, Response<MoviesResponseAll> response) {

                MoviesResponseAll moviesResponseAll =response.body();

                if(moviesResponseAll.movieResults!=null){

                    movieResults.clear();
                    movieResults.addAll(moviesResponseAll.movieResults);
                    moviesAdapter.notifyDataSetChanged();

                }



                NowShowing.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                googleProgressBar.setVisibility(View.GONE);






            }

            @Override
            public void onFailure(Call<MoviesResponseAll> call, Throwable t) {

             //  recyclerView.setVisibility(View.VISIBLE);
               // googleProgressBar.setVisibility(View.VISIBLE);
                Log.d("ERROR",t.getLocalizedMessage());

            }
        });

    }






}





