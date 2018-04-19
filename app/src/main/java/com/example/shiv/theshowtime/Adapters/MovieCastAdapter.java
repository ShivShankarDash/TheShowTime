package com.example.shiv.theshowtime.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.Movies.MovieCastBrief;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CastViewHolder> {

    private Context mContext;
    private List<MovieCastBrief> mCasts;

    public MovieCastAdapter(Context mContext, List<MovieCastBrief> mCasts) {
        this.mContext = mContext;
        this.mCasts = mCasts;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CastViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_cast, parent, false));


        // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        Picasso.get().load(Constants.IMAGE_LOADING_BASE_URL_342 + mCasts.get(position).getProfilePath()).into(holder.castImageView);


        if (mCasts.get(position).getName() != null)
            holder.nameTextView.setText(mCasts.get(position).getName());
        else
            holder.nameTextView.setText("");

        if (mCasts.get(position).getCharacter() != null)
            holder.characterTextView.setText(mCasts.get(position).getCharacter());
        else
            holder.characterTextView.setText("");


    }

    @Override
    public int getItemCount() {
        return mCasts.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {


        public CircleImageView castImageView;
        public TextView nameTextView;
        public TextView characterTextView;

        public CastViewHolder(View itemView) {
            super(itemView);
            castImageView = (CircleImageView) itemView.findViewById(R.id.image_view_cast);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_cast_name);
            characterTextView = (TextView) itemView.findViewById(R.id.text_view_cast_as);


        }
    }


}
