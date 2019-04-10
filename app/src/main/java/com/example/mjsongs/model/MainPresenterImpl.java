package com.example.mjsongs.model;

import android.content.Context;

import com.example.mjsongs.presenter.MainPresenter;

import java.util.List;

public class MainPresenterImpl implements MainPresenter.Presenter, MainPresenter.GetSongsIntractor.OnFinishedListener {
    private MainPresenter.MainView mainView;
    private MainPresenter.GetSongsIntractor getSongsIntractor;

    public MainPresenterImpl(MainPresenter.MainView mainView, MainPresenter.GetSongsIntractor getSongsIntractor) {
        this.mainView = mainView;
        this.getSongsIntractor = getSongsIntractor;
    }

    @Override
    public void onDestroy() {
        mainView=null;
    }

    @Override
    public void onRefreshButtonClick() {
        if (mainView!=null){
            mainView.showProgress();
        }

    }

    @Override
    public void requestDataFromServer() {
        if (mainView!=null){
            mainView.showProgress();
        }
        getSongsIntractor.getSongsArrayList(this);

    }

    @Override
    public void showdetails(Context context,int position) {
        getSongsIntractor.showSongsDetails(context,position);
    }

    @Override
    public void onFinished(List<MJSongs> noticeArrayList) {
        if (mainView!=null){
        mainView.setDataToRecyclerView(noticeArrayList);
        mainView.hideProgress();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView!=null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }

}
