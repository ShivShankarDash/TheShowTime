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
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shiv.theshowtime.Adapters.MovieCastAdapter;
import com.example.shiv.theshowtime.Adapters.VideosAdapter;
import com.example.shiv.theshowtime.Adapters.ViewAllMoviesAdapter;
import com.example.shiv.theshowtime.MoviesAndTVShowsApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.Movies.Genres;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieCastBrief;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieCreditsResponse;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieDetails;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieResults;
import com.example.shiv.theshowtime.NetworkClasses.Movies.SimilarMoviesResponse;
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

public class MovieDetailActivity extends AppCompatActivity {

    private long mMovieId;

    private CollapsingToolbarLayout mCollapsingToolBarLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mtoolbar;
    private ConstraintLayout ToolbarConstraintLayout;
    private LinearLayout MovieRatingLinearLayout;

    private AVLoadingIndicatorView mPosterProgressBar;

    private RecyclerView mTrailerRecyclerView;
    private List<YoutubeVideo> mTrailers;
    private VideosAdapter mTrailerAdapter;
    private TextView mTrailerTextView;

    private TextView mCastTextView;
    private RecyclerView mCastRecyclerView;
    private List<MovieCastBrief> mCasts;
    private MovieCastAdapter mCastAdapter;


    private ImageView movieposter;
    private TextView ReadMoretext;
    private TextView OverViewtext;
    private TextView ReleaseAndRunTimeDatetext;
    //private TextView Runtimetext;
    private TextView MovieNametext;
    private TextView Moviegenretext;
    private TextView yearOfReleasetext;
    private TextView movieRating;
    private Call<MovieDetails> mMovieDetailsCall;
    private TextView revenueandbudget;
//private ImageView trailerPlayButton;

   private View horizontalLine;
    private LinearLayout mDetailsLayout;

    private TextView mSimilarMoviesTextView;
    private RecyclerView mSimilarMoviesRecyclerView;
    private ArrayList<MovieResults> mSimilarMovies;
    private ViewAllMoviesAdapter mSimilarMoviesAdapter;





    ArrayList<MovieDetails> movieDetailslist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        Intent receivedIntent = getIntent();
        mMovieId = receivedIntent.getLongExtra(Constants.MOVIE_ID, -1);
        if (mMovieId == -1) finish();


        movieposter = findViewById(R.id.movieposter);
        ReadMoretext = findViewById(R.id.text_view_read_more_movie_detail);
        OverViewtext = findViewById(R.id.text_view_overview_movie_detail);
        ReleaseAndRunTimeDatetext = findViewById(R.id.text_view_details_movie_detail);
        MovieNametext = findViewById(R.id.movie_name);
        Moviegenretext = findViewById(R.id.movie_genre_textView);
        yearOfReleasetext = findViewById(R.id.yearofrelease);
        revenueandbudget = findViewById(R.id.revenueandbudget);
        //   budget=findViewById(R.id.budget);
        mPosterProgressBar = findViewById(R.id.posterprogressbar);
        movieRating = findViewById(R.id.text_view_rating_movie_detail);
        mDetailsLayout = findViewById(R.id.layout_details_movie_detail);
        mAppBarLayout = findViewById(R.id.appbar);
        mCollapsingToolBarLayout = findViewById(R.id.collapsing_toolbar);
        mtoolbar = findViewById(R.id.toolbarmoviedetail);
        ToolbarConstraintLayout = findViewById(R.id.toolbarconstraintlayout);
        MovieRatingLinearLayout = findViewById(R.id.layout_rating_movie_detail);
        // trailerPlayButton=findViewById(R.id.trailerplayimage);
        mTrailerTextView = (TextView) findViewById(R.id.text_view_trailer_movie_detail);
        mTrailerRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_trailers_movie_detail);
        (new LinearSnapHelper()).attachToRecyclerView(mTrailerRecyclerView);
        mTrailers = new ArrayList<>();
        mTrailerAdapter = new VideosAdapter(MovieDetailActivity.this, mTrailers);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));

        mCastTextView = (TextView) findViewById(R.id.text_view_cast_movie_detail);
        mCastRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_cast_movie_detail);
        mCasts = new ArrayList<>();
        mCastAdapter = new MovieCastAdapter(MovieDetailActivity.this, mCasts);
        mCastRecyclerView.setAdapter(mCastAdapter);
        mCastRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        horizontalLine=findViewById(R.id.view_horizontal_line);


        mSimilarMoviesTextView = (TextView) findViewById(R.id.text_view_similar_movie_detail);
        mSimilarMoviesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_similar_movie_detail);
        mSimilarMovies = new ArrayList<>();
        mSimilarMoviesAdapter = new ViewAllMoviesAdapter(mSimilarMovies,MovieDetailActivity.this);
        mSimilarMoviesRecyclerView.setAdapter(mSimilarMoviesAdapter);
        mSimilarMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));





        loadActivity();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }

    private void loadActivity() {


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        Call<MovieDetails> call = moviesAndTVShowsApi.getMovieDetails(mMovieId, getString(R.string.Movie_DB_Api_key));

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, final Response<MovieDetails> response) {

                MovieDetails movieDetails = response.body();
                if (movieDetails == null) {
                    return;
                }

                mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                        if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                            if (response.body().getTitle() != null)
                                mCollapsingToolBarLayout.setTitle(response.body().getTitle());
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
                Picasso.get().load("http://image.tmdb.org/t/p/original" + response.body().getBackdropPath()).into(movieposter);

                if (response.body().getTitle() != null)
                    MovieNametext.setText(response.body().getTitle());

                else
                    MovieNametext.setText("");

                setGenres(response.body().getGenres());
                setYear(response.body().getReleaseDate());
                ToolbarConstraintLayout.setVisibility(View.VISIBLE);

                if (response.body().getVoteAverage() != null && response.body().getVoteAverage() != 0)
                    movieRating.setText(response.body().getVoteAverage() + "");
                MovieRatingLinearLayout.setVisibility(View.VISIBLE);

                if (response.body().getOverview() != null && !response.body().getOverview().trim().isEmpty()) {
                    ReadMoretext.setVisibility(View.VISIBLE);

                    OverViewtext.setVisibility(View.VISIBLE);
                    OverViewtext.setText(response.body().getOverview());
                    setTrailers();
                    setCasts();
                    setSimilarMovies();
                    ReadMoretext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OverViewtext.setMaxLines(Integer.MAX_VALUE);
                            mDetailsLayout.setVisibility(View.VISIBLE);
                            ReadMoretext.setVisibility(View.GONE);

                            setDetails(response.body().getReleaseDate(), response.body().getRuntime());
                            revenueandbudget.setText(response.body().getBudget() + "$\n" + response.body().getRevenue() + "$");

                        }
                    });
                } else
                    OverViewtext.setText("");


            }


            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });


    }

    private void setSimilarMovies() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
       Call<SimilarMoviesResponse> call= moviesAndTVShowsApi.getSimilarMovies((int)mMovieId,getString(R.string.Movie_DB_Api_key),1);

   call.enqueue(new Callback<SimilarMoviesResponse>() {
       @Override
       public void onResponse(Call<SimilarMoviesResponse> call, Response<SimilarMoviesResponse> response) {

           SimilarMoviesResponse similarMoviesResponse=response.body();
           if(similarMoviesResponse.getResults()!=null){

               mSimilarMovies.clear();
               mSimilarMovies.addAll(similarMoviesResponse.getResults());
               mSimilarMoviesAdapter.notifyDataSetChanged();


           }

           mSimilarMoviesTextView.setVisibility(View.VISIBLE);



       }

       @Override
       public void onFailure(Call<SimilarMoviesResponse> call, Throwable t) {

       }
   });






    }

    private void setCasts() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        Call<MovieCreditsResponse> call = moviesAndTVShowsApi.getMovieCredits((int) mMovieId, getString(R.string.Movie_DB_Api_key));
        call.enqueue(new Callback<MovieCreditsResponse>() {
            @Override
            public void onResponse(Call<MovieCreditsResponse> call, Response<MovieCreditsResponse> response) {

                MovieCreditsResponse movieCreditsResponse = response.body();
                if (movieCreditsResponse.getCasts() != null) {

                    mCasts.clear();
                    mCasts.addAll(movieCreditsResponse.getCasts());
                    mCastAdapter.notifyDataSetChanged();


                }
                mCastTextView.setVisibility(View.VISIBLE);
                mCastRecyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<MovieCreditsResponse> call, Throwable t) {

            }
        });


    }

    private void setTrailers() {


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAndTVShowsApi moviesAndTVShowsApi = retrofit.create(MoviesAndTVShowsApi.class);
        Call<YoutubeVideoResponse> call = moviesAndTVShowsApi.getMovieTrailers((int) mMovieId, getResources().getString(R.string.Movie_DB_Api_key));
        call.enqueue(new Callback<YoutubeVideoResponse>() {
            @Override
            public void onResponse(Call<YoutubeVideoResponse> call, Response<YoutubeVideoResponse> response) {

                YoutubeVideoResponse youtubeVideoResponse = response.body();
                Log.d("TAGGER", "Array : " + response.body().getVideoresults().get(0).getKey());

                if (youtubeVideoResponse.getVideoresults() != null) {

                    mTrailers.clear();
                    mTrailers.addAll(youtubeVideoResponse.getVideoresults());
                    Log.d("TAGGER", "Array : " + mTrailers.get(0).getKey());
                    mTrailerAdapter.notifyDataSetChanged();


                }
                if (!mTrailers.isEmpty()) {
                    mTrailerTextView.setVisibility(View.VISIBLE);
                    mTrailerRecyclerView.setVisibility(View.VISIBLE);
                    //  trailerPlayButton.setVisibility(View.VISIBLE);
                    horizontalLine.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<YoutubeVideoResponse> call, Throwable t) {

            }
        });


    }

    private void setDetails(String releaseString, Integer runtime) {

        String detailsString = "";

        if (releaseString != null && !releaseString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d, yyyy");
            try {
                Date releaseDate = sdf1.parse(releaseString);
                detailsString += sdf2.format(releaseDate) + "\n";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            detailsString = "-\n";
        }


        if (runtime != null && runtime != 0) {
            if (runtime < 60) {
                detailsString += runtime + " min(s)";
            } else {
                detailsString += runtime / 60 + " hr " + runtime % 60 + " mins";
            }
        } else {
            detailsString += "-";
        }

        ReleaseAndRunTimeDatetext.setText(detailsString);

    }

    private void setYear(String releaseDateString) {
        if (releaseDateString != null && !releaseDateString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(releaseDateString);
                yearOfReleasetext.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            yearOfReleasetext.setText("");
        }


    }

    private void setGenres(List<Genres> genresList) {

        String genres = "";
        if (genresList != null) {
            for (int i = 0; i < genresList.size(); i++) {
                if (genresList.get(i) == null) continue;
                if (i == genresList.size() - 1) {
                    genres = genres.concat(genresList.get(i).getGenreName());
                } else {
                    genres = genres.concat(genresList.get(i).getGenreName() + ", ");
                }
            }
        }


        Moviegenretext.setText(genres);
    }

}
