package com.example.shiv.theshowtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shiv.theshowtime.Adapters.TVShowCastAdapter;
import com.example.shiv.theshowtime.Adapters.VideosAdapter;
import com.example.shiv.theshowtime.Adapters.ViewAllTVShowsAdapter;
import com.example.shiv.theshowtime.MoviesAndTVShowsApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShow;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowBrief;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowCastBrief;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowCreditsResponse;
import com.example.shiv.theshowtime.NetworkClasses.YoutubeVideo;
import com.example.shiv.theshowtime.NetworkClasses.YoutubeVideoResponse;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVShowDetailActivity extends AppCompatActivity {

    private long mTVShowId;

    private CollapsingToolbarLayout mCollapsingToolBarLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mtoolbar;
    private ConstraintLayout ToolbarConstraintLayout;
    private LinearLayout TVShowRatingLinearLayout;

    private AVLoadingIndicatorView mPosterProgressBar;

    private RecyclerView mTVShowVideoRecyclerView;
    private List<YoutubeVideo> mTvShowsTrailers;
    private VideosAdapter mTVShowsVideoAdapter;
    private TextView mVideoTextView;

    private TextView mCastTextView;
    private RecyclerView mCastRecyclerView;
    private List<TVShowCastBrief> mCasts;
    private TVShowCastAdapter mCastAdapter;


    private ImageView tvshowposter;
    private TextView ReadMoretext;
    private TextView OverViewtext;
    private TextView FirstAirDateAndRunTimeDatetext;
    //private TextView Runtimetext;
    private TextView TVNametext;
    private TextView TVShowgenretext;
    private TextView yearOfReleasetext;
    private TextView TvshowRating;
    //   private Call<MovieDetails> mMovieDetailsCall;
    //private TextView revenueandbudget;
//private ImageView trailerPlayButton;

    private View horizontalLine;
    private LinearLayout mDetailsLayout;

    private TextView mSimilarTVShowsTextView;
    private RecyclerView mSimilarTVShowsRecyclerView;
    private ArrayList<TVShowBrief> mSimilarMovies;
    private ViewAllTVShowsAdapter mSimilarTVShowsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        Intent receivedIntent = getIntent();
        mTVShowId = receivedIntent.getIntExtra(Constants.TV_SHOW_ID, -1);
        if (mTVShowId == -1) finish();
        mtoolbar = findViewById(R.id.toolbartvshowdetail);
        ToolbarConstraintLayout = findViewById(R.id.toolbarconstraintlayout);
        mAppBarLayout = findViewById(R.id.appbar_tvshow);
        mCollapsingToolBarLayout = findViewById(R.id.collapsing_toolbar_tv_show);
        tvshowposter = findViewById(R.id.tvshowposter);
        mPosterProgressBar = findViewById(R.id.posterprogressbar);
        TVShowRatingLinearLayout = findViewById(R.id.layout_rating_tv_show_detail);
        ReadMoretext = findViewById(R.id.text_view_read_more_tv_show_detail);
        OverViewtext = findViewById(R.id.text_view_overview_tv_show_detail);
        FirstAirDateAndRunTimeDatetext = findViewById(R.id.text_view_details_tvshows_detail);
        TVNametext = findViewById(R.id.tvshow_name);
        TVShowgenretext = findViewById(R.id.tv_genre_textView);
        yearOfReleasetext = findViewById(R.id.yearofreleasetvshow);
        TvshowRating = findViewById(R.id.text_view_rating_tv_show_detail);
        mDetailsLayout = findViewById(R.id.layout_details_tv_show_detail);
        horizontalLine = findViewById(R.id.view_horizontal_line);
        mVideoTextView = findViewById(R.id.text_view_video_tv_show_detail);
        mTVShowVideoRecyclerView = findViewById(R.id.recycler_view_videos_tv_show_detail);
        mCastTextView = findViewById(R.id.text_view_cast_tv_show_detail);
        mCastRecyclerView = findViewById(R.id.recycler_view_cast_tv_show_detail);
        mSimilarTVShowsTextView = findViewById(R.id.text_view_similar_tv_show_detail);
        mSimilarTVShowsRecyclerView = findViewById(R.id.recycler_view_similar_tv_show_detail);


        mTvShowsTrailers = new ArrayList<>();
        mTVShowsVideoAdapter = new VideosAdapter(this, mTvShowsTrailers);
        mTVShowVideoRecyclerView.setAdapter(mTVShowsVideoAdapter);
        mTVShowVideoRecyclerView.setLayoutManager(new LinearLayoutManager(TVShowDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));


        mCastTextView = findViewById(R.id.text_view_cast_tv_show_detail);
        mCasts = new ArrayList<>();
        mCastRecyclerView = findViewById(R.id.recycler_view_cast_tv_show_detail);
        mCastAdapter = new TVShowCastAdapter(this, mCasts);
        mCastRecyclerView.setAdapter(mCastAdapter);
        mCastRecyclerView.setLayoutManager(new LinearLayoutManager(TVShowDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));


        loadTVShowActivity();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadTVShowActivity() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        Call<TVShow> call = moviesAndTVShowsApi.getTVShowDetails(mTVShowId, getString(R.string.Movie_DB_Api_key));

        call.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(Call<TVShow> call, final Response<TVShow> response) {

                TVShow tvshowdetails = response.body();
                if (tvshowdetails == null) {
                    return;
                }

                mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                        if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                            if (response.body().getName() != null)
                                mCollapsingToolBarLayout.setTitle(response.body().getName());
                            else
                                mCollapsingToolBarLayout.setTitle("");
                            mtoolbar.setVisibility(View.VISIBLE);

                        } else {

                            mCollapsingToolBarLayout.setTitle("");
                            mtoolbar.setVisibility(View.INVISIBLE);

                        }

                    }
                });
                mPosterProgressBar.setVisibility(View.GONE);
                Picasso.get().load("http://image.tmdb.org/t/p/original" + response.body().getBackdropPath()).into(tvshowposter);

                if (response.body().getName() != null)
                    TVNametext.setText(response.body().getName());

                else
                    TVNametext.setText("");

                //setGenres(response.body().getGenres());
                setYear(response.body().getFirstAirDate());
                ToolbarConstraintLayout.setVisibility(View.VISIBLE);

                if (response.body().getVoteAverage() != null && response.body().getVoteAverage() != 0)
                    TvshowRating.setText(response.body().getVoteAverage() + "");
                TVShowRatingLinearLayout.setVisibility(View.VISIBLE);

                if (response.body().getOverview() != null && !response.body().getOverview().trim().isEmpty()) {
                    ReadMoretext.setVisibility(View.VISIBLE);

                    OverViewtext.setVisibility(View.VISIBLE);
                    OverViewtext.setText(response.body().getOverview());
                    setVideos();
                    setTVShowCasts();
                    setSimilarTVShows();
                    ReadMoretext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OverViewtext.setMaxLines(Integer.MAX_VALUE);
                            mDetailsLayout.setVisibility(View.VISIBLE);
                            ReadMoretext.setVisibility(View.GONE);

                            setDetails(response.body().getFirstAirDate(), response.body().getEpisodeRunTime());
//                            revenueandbudget.setText(response.body().getBudget() + "$\n" + response.body().getRevenue() + "$");

                        }
                    });
                } else
                    OverViewtext.setText("");


            }


            @Override
            public void onFailure(Call<TVShow> call, Throwable t) {

            }
        });


    }

    private void setSimilarTVShows() {


    }

    private void setTVShowCasts() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        Call<TVShowCreditsResponse> call = moviesAndTVShowsApi.getTVShowCredits((int) mTVShowId, getResources().getString(R.string.Movie_DB_Api_key));

        call.enqueue(new Callback<TVShowCreditsResponse>() {
            @Override
            public void onResponse(Call<TVShowCreditsResponse> call, Response<TVShowCreditsResponse> response) {

                TVShowCreditsResponse tvShowCreditsResponse = response.body();
                if (tvShowCreditsResponse.getCasts() != null) {

                    mCasts.clear();
                    mCasts.addAll(tvShowCreditsResponse.getCasts());
                    mCastAdapter.notifyDataSetChanged();

                }
                if (mCasts != null) {
                    mCastTextView.setVisibility(View.VISIBLE);
                    mCastRecyclerView.setVisibility(View.VISIBLE);


                }


            }

            @Override
            public void onFailure(Call<TVShowCreditsResponse> call, Throwable t) {

            }
        });


    }

    private void setVideos() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        Call<YoutubeVideoResponse> call = moviesAndTVShowsApi.getTVShowVideos((int) mTVShowId, getResources().getString(R.string.Movie_DB_Api_key));
        call.enqueue(new Callback<YoutubeVideoResponse>() {
            @Override
            public void onResponse(Call<YoutubeVideoResponse> call, Response<YoutubeVideoResponse> response) {

                YoutubeVideoResponse youtubeVideoResponse = response.body();
                //Log.d("TAGGER", "Array : " + response.body().getVideoresults().get(0).getKey());

                if (youtubeVideoResponse.getVideoresults() != null) {

                    mTvShowsTrailers.clear();
                    mTvShowsTrailers.addAll(youtubeVideoResponse.getVideoresults());
                    //  Log.d("TAGGER", "Array : " + mTrailers.get(0).getKey());
                    mTVShowsVideoAdapter.notifyDataSetChanged();


                }
                if (!mTvShowsTrailers.isEmpty()) {
                    mVideoTextView.setVisibility(View.VISIBLE);
                    mTVShowVideoRecyclerView.setVisibility(View.VISIBLE);
                    //  trailerPlayButton.setVisibility(View.VISIBLE);
                    horizontalLine.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<YoutubeVideoResponse> call, Throwable t) {

            }
        });


    }

    private void setYear(String firstAirDateString) {
        if (firstAirDateString != null && !firstAirDateString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date firstAirDate = sdf1.parse(firstAirDateString);
                yearOfReleasetext.setText(sdf2.format(firstAirDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            yearOfReleasetext.setText("");
        }
    }

    private void setDetails(String firstAirDateString, List<Integer> runtime) {
        String detailsString = "";

        if (firstAirDateString != null && !firstAirDateString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d, yyyy");
            try {
                Date releaseDate = sdf1.parse(firstAirDateString);
                detailsString += sdf2.format(releaseDate) + "\n";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            detailsString = "-\n";
        }

        if (runtime != null && !runtime.isEmpty() && runtime.get(0) != 0) {
            if (runtime.get(0) < 60) {
                detailsString += runtime.get(0) + " min(s)" + "\n";
            } else {
                detailsString += runtime.get(0) / 60 + " hr " + runtime.get(0) % 60 + " mins" + "\n";
            }
        } else {
            detailsString += "-\n";
        }

        FirstAirDateAndRunTimeDatetext.setText(detailsString);

    }


}
