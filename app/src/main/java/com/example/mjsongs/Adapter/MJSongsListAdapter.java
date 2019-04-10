package com.example.mjsongs.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mjsongs.R;
import com.example.mjsongs.model.MJSongs;
import com.example.mjsongs.model.MJSongsList;
import com.example.mjsongs.presenter.OnItemClickListener;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MJSongsListAdapter extends RecyclerView.Adapter<MJSongsListAdapter.ViewHolder> {
    Context context;
    List<MJSongs>mjSongs;
    OnItemClickListener onItemClickListener;

    public MJSongsListAdapter(Context context, List<MJSongs> mjSongs,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mjSongs = mjSongs;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_song_item,parent,false);
        return new ViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MJSongs mjSongsList=mjSongs.get(position);
        holder.songsName.setText(mjSongsList.getTrackName());
        long minutes=(mjSongsList.getTrackTimeMillis()/1000)/60;
        long seconds=(mjSongsList.getTrackTimeMillis()/1000)%60;
        String dur=(String.valueOf(minutes))+":"+String.valueOf(seconds);
        holder.duration.setText(dur);
//        Drawable drawable=LoadImageFromWebOperations(mjSongsList.getArtworkUrl100());
//        holder.artistImg.setImageURI();
        String url=mjSongsList.getArtworkUrl100();
        Glide.with(context)
                .load(url)
                .into(holder.artistImg);

    }

    @Override
    public int getItemCount() {
        return mjSongs.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView songsName,duration;
        ImageView artistImg;
        private OnItemClickListener onItemClickListener;


        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            songsName=itemView.findViewById(R.id.txtSongName);
            duration=itemView.findViewById(R.id.txtDuration);
            artistImg=itemView.findViewById(R.id.imgView);
            this.onItemClickListener=onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getLayoutPosition());
        }
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "image");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
