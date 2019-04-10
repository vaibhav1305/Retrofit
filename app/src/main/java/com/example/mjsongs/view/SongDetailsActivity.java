package com.example.mjsongs.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mjsongs.R;
import com.example.mjsongs.model.MJSongs;

import java.util.List;

public class SongDetailsActivity extends AppCompatActivity {
Intent intent;
Bundle bundle;
MJSongs list;
ImageView imageView;
TextView songName,artistName,collectionName,releaseDate,generName,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        bundle=getIntent().getBundleExtra("Bundle");
        list= (MJSongs) bundle.getSerializable("List");
        imageView=findViewById(R.id.artistImage);
        songName=findViewById(R.id.songName);
        artistName=findViewById(R.id.artistName);
        collectionName=findViewById(R.id.collectionName);
        releaseDate=findViewById(R.id.releaseDate);
        generName=findViewById(R.id.GenreName);
        country=findViewById(R.id.countryName);
        setData();

    }
    private void setData(){
        String url=list.getArtworkUrl100();
        Glide.with(SongDetailsActivity.this)
                .load(url)
                .into(imageView);
        songName.setText("Name : "+list.getTrackName());
        artistName.setText("Artist : "+list.getArtistName());
        collectionName.setText("Collection : "+list.getCollectionName());
        releaseDate.setText("Release On : "+list.getReleaseDate());
        generName.setText("Genre Name : "+list.getPrimaryGenreName());
        country.setText("Country : "+list.getCountry());
    }
}
