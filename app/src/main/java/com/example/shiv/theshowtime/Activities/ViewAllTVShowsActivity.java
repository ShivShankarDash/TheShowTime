package com.example.shiv.theshowtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.shiv.theshowtime.Adapters.ViewAllTVShowsAdapter;
import com.example.shiv.theshowtime.MoviesAndTVShowsApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowBrief;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowsResponseAll;
import com.example.shiv.theshowtime.R;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllTVShowsActivity extends AppCompatActivity {

    private SmoothProgressBar mSmoothProgressBar;

    private RecyclerView mRecyclerView;
    private List<TVShowBrief> mTVShows;
    private ViewAllTVShowsAdapter mTVShowsAdapter;

    private int mTVShowType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    private Call<TVShowsResponseAll> mAiringTodayTVShowsCall;
    private Call<TVShowsResponseAll> mOnTheAirTVShowsCall;
    private Call<TVShowsResponseAll> mPopularTVShowsCall;
    private Call<TVShowsResponseAll> mTopRatedTVShowsCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tvshows);


        Intent receivedIntent = getIntent();
        mTVShowType = receivedIntent.getIntExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, -1);

        if (mTVShowType == -1) finish();

        switch (mTVShowType) {
            case Constants.AIRING_TODAY_TV_SHOWS_TYPE:
                setTitle("Airing Today TV Shows");
                break;
            case Constants.ON_THE_AIR_TV_SHOWS_TYPE:
                setTitle("On Air TV Shows");
                break;
            case Constants.POPULAR_TV_SHOWS_TYPE:
                setTitle("Popular TV Shows");
                break;
            case Constants.TOP_RATED_TV_SHOWS_TYPE:
                setTitle("Top Rated TV Shows");
                break;
        }

        mSmoothProgressBar = (SmoothProgressBar) findViewById(R.id.smooth_progress_bar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_all_tv_shows);
        mTVShows = new ArrayList<>();
        mTVShowsAdapter = new ViewAllTVShowsAdapter(ViewAllTVShowsActivity.this, mTVShows);
        mRecyclerView.setAdapter(mTVShowsAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllTVShowsActivity.this, 3);
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
                    loadTVShows(mTVShowType);
                    loading = true;
                }

            }
        });

        loadTVShows(mTVShowType);


    }

    private void loadTVShows(int mTVShowType) {

        if (pagesOver) return;
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        mSmoothProgressBar.progressiveStart();


        switch (mTVShowType) {

            case Constants.AIRING_TODAY_TV_SHOWS_TYPE:
                final Call<TVShowsResponseAll> mNowShowingCall = moviesAndTVShowsApi.getAiringTodayTVShows(getString(R.string.Movie_DB_Api_key), presentPage);
                mNowShowingCall.enqueue(new Callback<TVShowsResponseAll>() {
                    @Override
                    public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                        TVShowsResponseAll TVShowsResponseAll = response.body();
                        mSmoothProgressBar.progressiveStop();
                        if (TVShowsResponseAll.getResults() != null) {

                            //mMovies.clear();
                            mTVShows.addAll(TVShowsResponseAll.getResults());


                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;


                    }

                    @Override
                    public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

                    }
                });
                break;
            case Constants.ON_THE_AIR_TV_SHOWS_TYPE:
                final Call<TVShowsResponseAll> mNowShowingCal2 = moviesAndTVShowsApi.getOnTheAirTVShows(getString(R.string.Movie_DB_Api_key), presentPage);
                mNowShowingCal2.enqueue(new Callback<TVShowsResponseAll>() {
                    @Override
                    public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                        TVShowsResponseAll TVShowsResponseAll = response.body();
                        mSmoothProgressBar.progressiveStop();
                        if (TVShowsResponseAll.getResults() != null) {

                            //mMovies.clear();
                            mTVShows.addAll(TVShowsResponseAll.getResults());


                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;


                    }

                    @Override
                    public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

                    }
                });

                break;

            case Constants.POPULAR_TV_SHOWS_TYPE:
                final Call<TVShowsResponseAll> mNowShowingCal3 = moviesAndTVShowsApi.getPopularTVShows(getString(R.string.Movie_DB_Api_key), presentPage);
                mNowShowingCal3.enqueue(new Callback<TVShowsResponseAll>() {
                    @Override
                    public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                        TVShowsResponseAll TVShowsResponseAll = response.body();
                        mSmoothProgressBar.progressiveStop();
                        if (TVShowsResponseAll.getResults() != null) {

                            //mMovies.clear();
                            mTVShows.addAll(TVShowsResponseAll.getResults());


                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;


                    }

                    @Override
                    public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

                    }
                });
                break;

            case Constants.TOP_RATED_TV_SHOWS_TYPE:
                final Call<TVShowsResponseAll> mNowShowingCal4 = moviesAndTVShowsApi.getTopRatedTVShows(getString(R.string.Movie_DB_Api_key), presentPage);
                mNowShowingCal4.enqueue(new Callback<TVShowsResponseAll>() {
                    @Override
                    public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                        TVShowsResponseAll TVShowsResponseAll = response.body();
                        mSmoothProgressBar.progressiveStop();
                        if (TVShowsResponseAll.getResults() != null) {

                            //mMovies.clear();
                            mTVShows.addAll(TVShowsResponseAll.getResults());


                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;


                    }

                    @Override
                    public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

                    }
                });
                break;

        }

    }


}
