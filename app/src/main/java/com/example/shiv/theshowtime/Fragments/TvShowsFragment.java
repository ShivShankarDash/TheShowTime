package com.example.shiv.theshowtime.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.shiv.theshowtime.Activities.ViewAllTVShowsActivity;
import com.example.shiv.theshowtime.Adapters.SmallTVShowsAdapter;
import com.example.shiv.theshowtime.Adapters.TVShowsAdapter;
import com.example.shiv.theshowtime.MoviesAndTVShowsApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.GenresList;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowBrief;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowsResponseAll;
import com.example.shiv.theshowtime.R;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsFragment extends Fragment {

    private GoogleProgressBar mProgressBar;
    private boolean mAiringTodaySectionLoaded;
    private boolean mOnTheAirSectionLoaded;
    private boolean mPopularSectionLoaded;
    private boolean mTopRatedSectionLoaded;

    private FrameLayout mAiringTodayLayout;
    private TextView mAiringTodayViewAllTextView;
    private RecyclerView mAiringTodayRecyclerView;
    private List<TVShowBrief> mAiringTodayTVShows;
    private TVShowsAdapter mAiringTodayAdapter;

    private FrameLayout mOnTheAirLayout;
    private TextView mOnTheAirViewAllTextView;
    private RecyclerView mOnTheAirRecyclerView;
    private List<TVShowBrief> mOnTheAirTVShows;
    private SmallTVShowsAdapter mOnTheAirAdapter;

    private FrameLayout mPopularLayout;
    private TextView mPopularViewAllTextView;
    private RecyclerView mPopularRecyclerView;
    private List<TVShowBrief> mPopularTVShows;
    private TVShowsAdapter mPopularAdapter;

    private FrameLayout mTopRatedLayout;
    private TextView mTopRatedViewAllTextView;
    private RecyclerView mTopRatedRecyclerView;
    private List<TVShowBrief> mTopRatedTVShows;
    private SmallTVShowsAdapter mTopRatedAdapter;

    private Call<GenresList> mGenresListCall;
    private Call<TVShowsResponseAll> mAiringTodayTVShowsCall;
    private Call<TVShowsResponseAll> mOnTheAirTVShowsCall;
    private Call<TVShowsResponseAll> mPopularTVShowsCall;
    private Call<TVShowsResponseAll> mTopRatedTVShowsCall;

    Drawable progressDrawable;


    public TvShowsFragment() {


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tv_shows_fragment,container,false);


        mProgressBar = (GoogleProgressBar) view.findViewById(R.id.progress_bar_tv_show);
        mProgressBar.setVisibility(View.GONE);
//        mAiringTodaySectionLoaded = false;
//        mOnTheAirSectionLoaded = false;
//        mPopularSectionLoaded = false;
//        mTopRatedSectionLoaded = false;
        progressDrawable = new ChromeFloatingCirclesDrawable.Builder(getContext())
                .colors(getProgressDrawableColors())
                .build();
        mProgressBar.setIndeterminateDrawable(progressDrawable);
        mAiringTodayLayout = (FrameLayout) view.findViewById(R.id.layout_airing_today);
        mOnTheAirLayout = (FrameLayout) view.findViewById(R.id.layout_on_the_air);
        mPopularLayout = (FrameLayout) view.findViewById(R.id.layout_popular);
        mTopRatedLayout = (FrameLayout) view.findViewById(R.id.layout_top_rated);

        mAiringTodayViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_airing_today);
        mOnTheAirViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_on_the_air);
        mPopularViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_popular);
        mTopRatedViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_top_rated);

        mAiringTodayRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_airing_today);
        (new LinearSnapHelper()).attachToRecyclerView(mAiringTodayRecyclerView);
        mOnTheAirRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_on_the_air);
        mPopularRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_popular);
        (new LinearSnapHelper()).attachToRecyclerView(mPopularRecyclerView);
        mTopRatedRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_top_rated);

        mAiringTodayTVShows = new ArrayList<>();
        mOnTheAirTVShows = new ArrayList<>();
        mPopularTVShows = new ArrayList<>();
        mTopRatedTVShows = new ArrayList<>();

        mAiringTodayAdapter = new TVShowsAdapter(getContext(), mAiringTodayTVShows);
        mOnTheAirAdapter = new SmallTVShowsAdapter(getContext(), mOnTheAirTVShows);
        mPopularAdapter = new TVShowsAdapter(getContext(), mPopularTVShows);
        mTopRatedAdapter = new SmallTVShowsAdapter(getContext(), mTopRatedTVShows);

        mAiringTodayRecyclerView.setAdapter(mAiringTodayAdapter);
        mAiringTodayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mOnTheAirRecyclerView.setAdapter(mOnTheAirAdapter);
        mOnTheAirRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mPopularRecyclerView.setAdapter(mPopularAdapter);
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter);
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mAiringTodayViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllTVShowsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.AIRING_TODAY_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });
        mOnTheAirViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllTVShowsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.ON_THE_AIR_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });
        mPopularViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllTVShowsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.POPULAR_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });
        mTopRatedViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllTVShowsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.TOP_RATED_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });


        loadAiringTodayTVShows();
        loadOnTheAirTVShows();
        loadPopularTVShows();
        loadTopRatedTVShows();







        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void loadPopularTVShows() {

        mPopularRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<TVShowsResponseAll> call= moviesAndTVShowsApi.getPopularTVShows(getString(R.string.Movie_DB_Api_key),1);

        call.enqueue(new Callback<TVShowsResponseAll>() {
            @Override
            public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                TVShowsResponseAll tvShowsResponseAll=response.body();

                if(tvShowsResponseAll.getResults()!=null){

                    mPopularTVShows.clear();
                    mPopularTVShows.addAll(tvShowsResponseAll.getResults());
                    mPopularAdapter.notifyDataSetChanged();

                }
                mPopularLayout.setVisibility(View.VISIBLE);
                mPopularRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

            }
        });






    }

    private void loadOnTheAirTVShows() {

        mOnTheAirRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<TVShowsResponseAll> call= moviesAndTVShowsApi.getOnTheAirTVShows(getString(R.string.Movie_DB_Api_key),1);

        call.enqueue(new Callback<TVShowsResponseAll>() {
            @Override
            public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                TVShowsResponseAll tvShowsResponseAll=response.body();

                if(tvShowsResponseAll.getResults()!=null){

                    mOnTheAirTVShows.clear();
                    mOnTheAirTVShows.addAll(tvShowsResponseAll.getResults());
                    mOnTheAirAdapter.notifyDataSetChanged();

                }
                mOnTheAirLayout.setVisibility(View.VISIBLE);
                mOnTheAirRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

            }
        });




    }

    private void loadTopRatedTVShows() {

        mTopRatedRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<TVShowsResponseAll> call= moviesAndTVShowsApi.getTopRatedTVShows(getString(R.string.Movie_DB_Api_key),1);

        call.enqueue(new Callback<TVShowsResponseAll>() {
            @Override
            public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                TVShowsResponseAll tvShowsResponseAll=response.body();

                if(tvShowsResponseAll.getResults()!=null){

                    mTopRatedTVShows.clear();
                    mTopRatedTVShows.addAll(tvShowsResponseAll.getResults());
                    mTopRatedAdapter.notifyDataSetChanged();

                }
               mTopRatedLayout.setVisibility(View.VISIBLE);
               mTopRatedRecyclerView.setVisibility(View.VISIBLE);
               mProgressBar.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

            }
        });


    }

    private void loadAiringTodayTVShows() {

        mAiringTodayRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi =retrofit.create(MoviesAndTVShowsApi.class);
        Call<TVShowsResponseAll> call= moviesAndTVShowsApi.getAiringTodayTVShows(getString(R.string.Movie_DB_Api_key),1);

        call.enqueue(new Callback<TVShowsResponseAll>() {
            @Override
            public void onResponse(Call<TVShowsResponseAll> call, Response<TVShowsResponseAll> response) {

                TVShowsResponseAll tvShowsResponseAll=response.body();

                if(tvShowsResponseAll.getResults()!=null){

                    mAiringTodayTVShows.clear();
                    mAiringTodayTVShows.addAll(tvShowsResponseAll.getResults());
                    mAiringTodayAdapter.notifyDataSetChanged();

                }
                mAiringTodayLayout.setVisibility(View.VISIBLE);
                mAiringTodayRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<TVShowsResponseAll> call, Throwable t) {

            }
        });


    }


    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        colors[0] = prefs.getInt(getString(R.string.firstcolor_pref_key),getResources().getColor(R.color.red));
        colors[1] = prefs.getInt(getString(R.string.secondcolor_pref_key),getResources().getColor(R.color.blue));
        colors[2] = prefs.getInt(getString(R.string.thirdcolor_pref_key),getResources().getColor(R.color.yellow));
        colors[3] = prefs.getInt(getString(R.string.fourthcolor_pref_key),getResources().getColor(R.color.green));
        return colors;
    }

}

