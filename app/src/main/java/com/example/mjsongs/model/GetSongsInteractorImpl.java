package com.example.mjsongs.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mjsongs.presenter.ApiInterface;
import com.example.mjsongs.presenter.MainPresenter;
import com.example.mjsongs.util.APIClient;
import com.example.mjsongs.view.SongDetailsActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSongsInteractorImpl implements MainPresenter.GetSongsIntractor {
    List<MJSongs> noticeArrayList;
    @Override
    public void getSongsArrayList(final OnFinishedListener onFinishedListener) {
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<MJSongsList> call=apiInterface.getMJSongsList("Michael+jackson");
        call.enqueue(new Callback<MJSongsList>() {
            @Override
            public void onResponse(Call<MJSongsList> call, Response<MJSongsList> response) {
                onFinishedListener.onFinished(response.body().getResults());
                noticeArrayList=response.body().getResults();

            }

            @Override
            public void onFailure(Call<MJSongsList> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void showSongsDetails(Context context,int postion) {
        MJSongs mjSongs=noticeArrayList.get(postion);
        Intent intent =new Intent(context, SongDetailsActivity.class);

        Bundle bundle=new Bundle();
        bundle.putSerializable("List", (Serializable) mjSongs);
        intent.putExtra("Bundle",bundle);
        context.startActivity(intent);
    }
}
