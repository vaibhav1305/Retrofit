package com.example.mjsongs.presenter;

import com.example.mjsongs.model.MJSongsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search")
    Call<MJSongsList>getMJSongsList(@Query("term")String term);
}
