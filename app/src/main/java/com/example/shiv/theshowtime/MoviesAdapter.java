package com.example.shiv.theshowtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    interface onItemClickListener{

        void onItemClick(int position);

    }



ArrayList<MovieResults> movieResults;
Context mcontext;
onItemClickListener listener;

    public MoviesAdapter(ArrayList<MovieResults> movieResults, Context mcontext, onItemClickListener listener) {
        this.movieResults = movieResults;
        this.mcontext = mcontext;
        this.listener = listener;
    }

    public MoviesAdapter(ArrayList<MovieResults> movieResults, Context mcontext) {
        this.movieResults = movieResults;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.movie_card,parent,false);

        return new ViewHolder(itemView);


    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

      MovieResults results=movieResults.get(position);
      holder.movieTitle.setText(results.getOriginTitle());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              listener.onItemClick(holder.getAdapterPosition());
          }
      });

        Picasso.get().load("http://image.tmdb.org/t/p/w780"+results.getBackdropPath()).into(holder.movieThumbnail);
       holder.rating.setText(results.getVotesAvg()+"");
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

    }

    @Override
    public int getItemCount() {

        return movieResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

 TextView movieTitle,rating;
 ImageView movieThumbnail,menuMore;
 View itemView;

    public ViewHolder(View itemView) {

        super(itemView);
        this.itemView=itemView;
        movieThumbnail=itemView.findViewById(R.id.moviethumb);
        movieTitle=itemView.findViewById(R.id.movietitle);
        menuMore=itemView.findViewById(R.id.menumore);
        rating=itemView.findViewById(R.id.rating);
    }
}

}
