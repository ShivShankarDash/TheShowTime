package com.example.shiv.theshowtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shiv.theshowtime.MoviesApi;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.Genres;
import com.example.shiv.theshowtime.NetworkClasses.MovieDetails;
import com.example.shiv.theshowtime.NetworkClasses.MovieResults;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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


private ImageView movieposter;
private TextView ReadMoretext;
private TextView OverViewtext;
private TextView ReleaseAndRunTimeDatetext;
//private TextView Runtimetext;
private TextView MovieNametext;
private  TextView Moviegenretext;
private TextView yearOfReleasetext;
private TextView movieRating;
private Call<MovieDetails> mMovieDetailsCall;
private TextView revenueandbudget;


private LinearLayout mDetailsLayout;


ArrayList<MovieDetails> movieDetailslist=new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        Intent receivedIntent=getIntent();
        mMovieId=receivedIntent.getLongExtra(Constants.MOVIE_ID,-1);
        if(mMovieId==-1) finish();


    movieposter=findViewById(R.id.movieposter);
    ReadMoretext=findViewById(R.id.text_view_read_more_movie_detail);
    OverViewtext=findViewById(R.id.text_view_overview_movie_detail);
    ReleaseAndRunTimeDatetext=findViewById(R.id.text_view_details_movie_detail);
    MovieNametext=findViewById(R.id.movie_name);
    Moviegenretext=findViewById(R.id.movie_genre_textView);
    yearOfReleasetext=findViewById(R.id.yearofrelease);
    revenueandbudget=findViewById(R.id.revenueandbudget);
 //   budget=findViewById(R.id.budget);
    mPosterProgressBar=findViewById(R.id.posterprogressbar);
    movieRating=findViewById(R.id.text_view_rating_movie_detail);
    mDetailsLayout=findViewById(R.id.layout_details_movie_detail);
    mAppBarLayout=findViewById(R.id.appbar);
    mCollapsingToolBarLayout=findViewById(R.id.collapsing_toolbar);
    mtoolbar=findViewById(R.id.toolbarmoviedetail);
    ToolbarConstraintLayout=findViewById(R.id.toolbarconstraintlayout);
    MovieRatingLinearLayout=findViewById(R.id.layout_rating_movie_detail);

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


        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesApi moviesApi=retrofit.create(MoviesApi.class);
        Call<MovieDetails> call=moviesApi.getMovieDetails(mMovieId,getString(R.string.Movie_DB_Api_key));

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, final Response<MovieDetails> response) {

                MovieDetails movieDetails=response.body();
                if(movieDetails==null){
                    return;
                }

             mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                 @Override
                 public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                     if(appBarLayout.getTotalScrollRange()+verticalOffset==0){
                         if (response.body().getTitle()!=null)
                             mCollapsingToolBarLayout.setTitle(response.body().getTitle());
                         else
                             mCollapsingToolBarLayout.setTitle("");
                         mtoolbar.setVisibility(View.VISIBLE);

                     }

                     else{

                         mCollapsingToolBarLayout.setTitle("");
                         mtoolbar.setVisibility(View.INVISIBLE);

                     }

                 }
             });
                 mPosterProgressBar.setVisibility(View.GONE);
                Picasso.get().load("http://image.tmdb.org/t/p/original"+response.body().getBackdropPath()).into(movieposter);

              if(response.body().getTitle()!=null)
                  MovieNametext.setText(response.body().getTitle());

              else
                  MovieNametext.setText("");

              setGenres(response.body().getGenres());
              setYear(response.body().getReleaseDate());
              ToolbarConstraintLayout.setVisibility(View.VISIBLE);

              if(response.body().getVoteAverage()!=null&&response.body().getVoteAverage()!=0)
              movieRating.setText(response.body().getVoteAverage()+"");
              MovieRatingLinearLayout.setVisibility(View.VISIBLE);

              if(response.body().getOverview()!=null && !response.body().getOverview().trim().isEmpty()) {
                  ReadMoretext.setVisibility(View.VISIBLE);

                  OverViewtext.setVisibility(View.VISIBLE);
                  OverViewtext.setText(response.body().getOverview());
                  ReadMoretext.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          OverViewtext.setMaxLines(Integer.MAX_VALUE);
                          mDetailsLayout.setVisibility(View.VISIBLE);
                          ReadMoretext.setVisibility(View.GONE);

                      }
                  });
              }
               else
                   OverViewtext.setText("");

               setDetails(response.body().getReleaseDate(),response.body().getRuntime());
               revenueandbudget.setText(response.body().getBudget()+"$\n"+response.body().getRevenue()+"$");

            }





            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

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

    String genres="";
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
