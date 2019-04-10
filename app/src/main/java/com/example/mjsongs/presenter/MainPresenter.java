package com.example.mjsongs.presenter;

import android.content.Context;

import com.example.mjsongs.model.MJSongs;

import java.util.ArrayList;
import java.util.List;

public interface MainPresenter {
    interface Presenter{
        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();
        void showdetails(Context context,int position);
    }

    interface MainView{
        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<MJSongs> noticeArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface GetSongsIntractor {

        interface OnFinishedListener {
            void onFinished(List<MJSongs> noticeArrayList);
            void onFailure(Throwable t);
        }

        void getSongsArrayList(OnFinishedListener onFinishedListener);
        void showSongsDetails(Context context,int position);
    }


}
