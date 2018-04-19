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

import com.example.shiv.theshowtime.Activities.TVShowDetailActivity;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowBrief;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewAllTVShowsAdapter extends RecyclerView.Adapter<ViewAllTVShowsAdapter.ViewHolder> {

    onItemClickListener onItemClickListener;
    private Context mcontext;
    private List<TVShowBrief> mTVShows;

    public ViewAllTVShowsAdapter(Context mcontext, List<TVShowBrief> mTVShows, onItemClickListener onItemClickListener) {
        this.mcontext = mcontext;
        this.mTVShows = mTVShows;
        this.onItemClickListener = onItemClickListener;
    }


    public ViewAllTVShowsAdapter(Context mcontext, List<TVShowBrief> mTVShows) {
        this.mcontext = mcontext;
        this.mTVShows = mTVShows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_tv_show_card, parent, false);


        return new ViewAllTVShowsAdapter.ViewHolder(itemView);


        //   return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TVShowBrief results = mTVShows.get(position);
        holder.TvShowTitle.setText(results.getOriginalName());

        Picasso.get().load("http://image.tmdb.org/t/p/w780" + results.getBackdropPath()).into(holder.smallTvShowPoster);
        //   holder.rating.setText(results.getVoteAverage()+"");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, TVShowDetailActivity.class);
                intent.putExtra(Constants.TV_SHOW_ID, results.getId());
                mcontext.startActivity(intent);

            }
        });

        holder.smallTvShowPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, TVShowDetailActivity.class);
                intent.putExtra(Constants.TV_SHOW_ID, results.getId());
                mcontext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mTVShows.size();
    }

    interface onItemClickListener {

        void onItemClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView smallTvShowPoster;
        TextView TvShowTitle;
        ImageButton favadder;
        View itemView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            smallTvShowPoster = itemView.findViewById(R.id.tvthumbsmall);
            TvShowTitle = itemView.findViewById(R.id.text_view_tvtitle_show_card);
            favadder = itemView.findViewById(R.id.image_button_fav_show_card);


        }
    }


}
