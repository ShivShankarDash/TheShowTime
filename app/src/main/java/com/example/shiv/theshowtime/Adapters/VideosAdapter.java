package com.example.shiv.theshowtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiv.theshowtime.Activities.VideoPlayer;
import com.example.shiv.theshowtime.NetworkClasses.Constants;
import com.example.shiv.theshowtime.NetworkClasses.YoutubeVideo;
import com.example.shiv.theshowtime.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewVideoCardHolder> {

    private Context mContext;
    private List<YoutubeVideo> mVideos;


    public VideosAdapter(Context mContext, List<YoutubeVideo> mVideos) {
        this.mContext = mContext;
        this.mVideos = mVideos;
        Log.d("TAGGER", "SIZE " + mVideos.size());
    }


    @NonNull
    @Override
    public ViewVideoCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_layout, parent, false);

        return new ViewVideoCardHolder(itemView);


//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewVideoCardHolder holder, int position) {


        //TODO : check for empty element
        if (!mVideos.get(position).getKey().isEmpty()) {
            Log.d("LOGGER", Constants.YOUTUBE_THUMBNAIL_BASE_URL + mVideos.get(position).getKey() + Constants.YOUTUBE_THUMBNAIL_IMAGE_QUALITY);
            Picasso.get().load(Constants.YOUTUBE_THUMBNAIL_BASE_URL + mVideos.get(position).getKey() + Constants.YOUTUBE_THUMBNAIL_IMAGE_QUALITY).placeholder(R.mipmap.youtube_icon_our_icon).into(holder.videoImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            });
            if (holder.videoImage != null) {

                holder.trailerplayimageview.setVisibility(View.VISIBLE);

            }
//
//
//            Log.d("TAGGER", Constants.YOUTUBE_WATCH_BASE_URL + mVideos.get(position).getKey() + Constants.YOUTUBE_THUMBNAIL_IMAGE_QUALITY);
//            if (mVideos.get(position).getName() != null)
//                holder.videoName.setText(mVideos.get(position).getName());
//            else
//                holder.videoName.setText("");


        }
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class ViewVideoCardHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView videoName;
        public ImageView videoImage;
        public ImageView trailerplayimageview;


        public ViewVideoCardHolder(final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_video);
            videoImage = itemView.findViewById(R.id.image_view_video);
            videoName = itemView.findViewById(R.id.text_view_video_name);
            trailerplayimageview = itemView.findViewById(R.id.trailerplayimage);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(itemView.getContext(), VideoPlayer.class);
                    Log.d("TAGGER", mVideos.get(getAdapterPosition()).getKey());
                    intent.putExtra("VideoKey", mVideos.get(getAdapterPosition()).getKey());
                    mContext.startActivity(intent);

                }
            });


        }
    }


}
