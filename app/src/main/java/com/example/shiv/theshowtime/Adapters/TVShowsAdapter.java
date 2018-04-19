package com.example.shiv.theshowtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.ViewHolder> {

    onItemClickListener listener;
    private Context mcontext;
    private List<TVShowBrief> mTVShows;

    public TVShowsAdapter(Context mcontext, List<TVShowBrief> mTVShows, onItemClickListener listener) {
        this.mcontext = mcontext;
        this.mTVShows = mTVShows;
        this.listener = listener;
    }

    public TVShowsAdapter(Context mContext, List<TVShowBrief> mTVShows) {
        this.mcontext = mContext;
        this.mTVShows = mTVShows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_show_card, parent, false);


        return new TVShowsAdapter.ViewHolder(itemView);


        // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TVShowBrief results = mTVShows.get(position);
        holder.tvShowTitle.setText(results.getOriginalName());

        Picasso.get().load("http://image.tmdb.org/t/p/w780" + results.getBackdropPath()).into(holder.TvShowThumbnail);
        holder.rating.setText(results.getVoteAverage() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, TVShowDetailActivity.class);
                intent.putExtra(Constants.TV_SHOW_ID, results.getId());
                //intent.putExtra(Constants.TV_SHOW_ID,results.getId());
                mcontext.startActivity(intent);

            }
        });

        holder.TvShowThumbnail.setOnClickListener(new View.OnClickListener() {
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

        TextView tvShowTitle, rating;
        ImageView TvShowThumbnail;
        ImageButton favadder;
        View itemView;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            TvShowThumbnail = itemView.findViewById(R.id.tvshowthumb);
            tvShowTitle = itemView.findViewById(R.id.tvshowtitle);
            favadder = itemView.findViewById(R.id.favadder);
            rating = itemView.findViewById(R.id.tvshowrating);
            cardView = itemView.findViewById(R.id.card_view_tv_show);


        }
    }


}
