package com.example.shiv.theshowtime.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.TVShows.TVShowCastBrief;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TVShowCastAdapter extends RecyclerView.Adapter<TVShowCastAdapter.CastViewHolder> {

    private Context mContext;
    private List<TVShowCastBrief> mCasts;


    public TVShowCastAdapter(Context mContext, List<TVShowCastBrief> mCasts) {
        this.mContext = mContext;
        this.mCasts = mCasts;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TVShowCastAdapter.CastViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tv_show_cast, parent, false));


        //     return null;
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
        return 0;
    }


    public class CastViewHolder extends RecyclerView.ViewHolder {


        public CircleImageView castImageView;
        public TextView nameTextView;
        public TextView characterTextView;

        public CastViewHolder(View itemView) {
            super(itemView);
            castImageView = (CircleImageView) itemView.findViewById(R.id.image_view_tv_show_cast);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_tv_show_cast_name);
            characterTextView = (TextView) itemView.findViewById(R.id.text_view_cast_as_tv_show);

        }
    }


}
