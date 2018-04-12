package com.example.shiv.theshowtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.shiv.theshowtime.Adapters.SmallMoviesAdapter;
import com.example.shiv.theshowtime.Adapters.ViewAllMoviesAdapter;
import com.example.shiv.theshowtime.MoviesApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.MovieResults;
import com.example.shiv.theshowtime.NetworkClasses.MoviesResponseAll;
import com.example.shiv.theshowtime.R;

import java.util.ArrayList;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllMoviesActivity extends AppCompatActivity implements Callback<MoviesResponseAll>{


    private SmoothProgressBar msmoothProgressBar;

    private RecyclerView mRecyclerView;
    private ArrayList<MovieResults> mMovies;
    private ViewAllMoviesAdapter mMoviesAdapter;

    private int mMovieType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;


    private Call<MoviesResponseAll> mNowShowingCall;
    private Call<MoviesResponseAll> mPopularCall;
    private Call<MoviesResponseAll> mUpcomingCall;
    private Call<MoviesResponseAll> mTopRatedCall;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_movies);

        Intent receivedIntent = getIntent();
        mMovieType = receivedIntent.getIntExtra(Constants.VIEW_ALL_MOVIES_TYPE, -1);
        if (mMovieType == -1) finish();

        switch (mMovieType) {

            case Constants.NOW_SHOWING_MOVIES_TYPE:
                setTitle("In Theatres");
                break;

            case Constants.POPULAR_MOVIES_TYPE:
                setTitle("Popular Movies");
                break;

            case Constants.UPCOMING_MOVIES_TYPE:
                setTitle("Upcoming");
                break;

            case Constants.TOP_RATED_MOVIES_TYPE:
                setTitle("Top Rated");
                break;

        }
        msmoothProgressBar = (SmoothProgressBar) findViewById(R.id.smooth_progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_all);
        mMovies = new ArrayList<>();
        mMoviesAdapter = new ViewAllMoviesAdapter(mMovies, ViewAllMoviesActivity.this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllMoviesActivity.this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadMovies(mMovieType);
                    loading = true;
                }





            }
        });


        loadMovies(mMovieType);



    }






    private void loadMovies(int mMovieType) {

        if(pagesOver) return;
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MoviesApi moviesApi=retrofit.create(MoviesApi.class);
        msmoothProgressBar.progressiveStart();

        switch (mMovieType){

            case Constants.NOW_SHOWING_MOVIES_TYPE:
                 final Call<MoviesResponseAll> mNowShowingCall = moviesApi.getlatest(getString(R.string.Movie_DB_Api_key), "en-US", presentPage);
                 mNowShowingCall.enqueue(this);

            break;
            case Constants.POPULAR_MOVIES_TYPE:
                Call<MoviesResponseAll> mNowShowingCall1=moviesApi.getPopular(getString(R.string.Movie_DB_Api_key),"en-US",presentPage);
                mNowShowingCall1.enqueue(this);

                break;

            case Constants.UPCOMING_MOVIES_TYPE:
                Call<MoviesResponseAll> mNowShowingCall2=moviesApi.getUpcoming(getString(R.string.Movie_DB_Api_key),"en-US",presentPage);
                mNowShowingCall2.enqueue(this);
                break;

            case Constants.TOP_RATED_MOVIES_TYPE:
                Call<MoviesResponseAll> mNowShowingCall3=moviesApi.getTopRated(getString(R.string.Movie_DB_Api_key),"en-US",presentPage);
                mNowShowingCall3.enqueue(this);
                break;

        }



    }

    @Override
    public void onResponse(Call<MoviesResponseAll> call, Response<MoviesResponseAll> response) {
        MoviesResponseAll moviesResponseAll =response.body();
        msmoothProgressBar.progressiveStop();
        if(moviesResponseAll.movieResults!=null){

            //mMovies.clear();
            mMovies.addAll(moviesResponseAll.movieResults);


        }
        mMoviesAdapter.notifyDataSetChanged();
        if(response.body().getPage()==response.body().getTotalpages())
            pagesOver=true;
        else
            presentPage++;
            //mNowShowingCall.enqueue(this);






    }

    @Override
    public void onFailure(Call<MoviesResponseAll> call, Throwable t) {

    }
}
