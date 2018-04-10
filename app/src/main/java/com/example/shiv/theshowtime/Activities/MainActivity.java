package com.example.shiv.theshowtime.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.shiv.theshowtime.Adapters.MoviesAdapter;
import com.example.shiv.theshowtime.Fragments.AllMoviesFragment;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.MovieResults;
import com.example.shiv.theshowtime.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    MoviesAdapter moviesAdapter;
    ArrayList<MovieResults> movieResults=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

   // recyclerView=findViewById(R.id.recyclerview);
    //progressBar=findViewById(R.id.progressBar);

//moviesAdapter=new MoviesAdapter(movieResults,MainActivity.this);
//RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);

//RecyclerView.LayoutManager layoutManager=new GridLayoutManager(MainActivity.this,2);
//recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//recyclerView.setLayoutManager(layoutManager);
////recyclerView.addItemDecoration(new GridLayoutManager.SpanSizeLookup());
//recyclerView.setItemAnimator(new DefaultItemAnimator());
//recyclerView.setAdapter(moviesAdapter);
//fetchmovieslist();


setTitle("MOVIES");
setFragment(new AllMoviesFragment());





    }
//
//    private void fetchmovieslist() {
//
//    recyclerView.setVisibility(View.GONE);
//    progressBar.setVisibility(View.VISIBLE);
//
//        final Retrofit retrofit=new Retrofit.Builder()
//                          .baseUrl("https://api.themoviedb.org/")
//                          .addConverterFactory(GsonConverterFactory.create())
//                          .build();
//        MoviesApi moviesApi=retrofit.create(MoviesApi.class);
//        Call<MoviesResponseAll> call=moviesApi.getlatest();
//
//        call.enqueue(new Callback<MoviesResponseAll>() {
//            @Override
//            public void onResponse(Call<MoviesResponseAll> call, Response<MoviesResponseAll> response) {
//
//                MoviesResponseAll tmdbClass=response.body();
//
//                if(tmdbClass.movieResults!=null){
//
//                    movieResults.clear();
//                    movieResults.addAll(tmdbClass.movieResults);
//                    moviesAdapter.notifyDataSetChanged();
//
//                }
//
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<MoviesResponseAll> call, Throwable t) {
//
//             recyclerView.setVisibility(View.VISIBLE);
//             progressBar.setVisibility(View.GONE);
//
//            }
//        });
//
//
//
//
//    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



        getMenuInflater().inflate(R.menu.search_menu,menu); 
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.movies) {
            setTitle("Movies");
            setFragment(new AllMoviesFragment());
            return true;


        } else if (id == R.id.tvshows) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        return false;
    }


    private void setFragment(Fragment fragment){

        FragmentManager fragmentManager=getSupportFragmentManager();


       // FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container,fragment);
        fragmentTransaction.commit();



    }



}
