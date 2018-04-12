package com.example.shiv.theshowtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiv.theshowtime.Activities.MovieDetailActivity;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.MovieResults;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAllMoviesAdapter extends RecyclerView.Adapter<ViewAllMoviesAdapter.ViewHolder> {

    interface onItemClickListener{

        void onItemClick(int position);


    }

    ArrayList<MovieResults> movieResults;
    Context mcontext;
    SmallMoviesAdapter.onItemClickListener listener;

    public ViewAllMoviesAdapter(ArrayList<MovieResults> movieResults, Context mcontext, SmallMoviesAdapter.onItemClickListener listener) {
        this.movieResults = movieResults;
        this.mcontext = mcontext;
        this.listener = listener;
    }

    public ViewAllMoviesAdapter(ArrayList<MovieResults> movieResults, Context mcontext) {
        this.movieResults = movieResults;
        this.mcontext = mcontext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.small_movie_card_view_all,parent,false);


        return new ViewAllMoviesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final MovieResults results=movieResults.get(position);
        holder.movieTitle.setText(results.getOriginTitle());
        Picasso.get().load("http://image.tmdb.org/t/p/w780"+results.getPosterPath()).fit().into(holder.smallPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // listener.onItemClick(holder.getAdapterPosition());
                Intent intent=new Intent(mcontext,MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,results.getId());
                mcontext.startActivity(intent);


            }
        });

        holder.smallPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,results.getId());
                mcontext.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return movieResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView smallPoster;
        TextView movieTitle;
        ImageButton favadder;
        View itemView;




        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            smallPoster=itemView.findViewById(R.id.moviethumbsmall);
            movieTitle=itemView.findViewById(R.id.text_view_title_show_card);
            favadder=itemView.findViewById(R.id.image_button_fav_show_card);






        }
    }













}
