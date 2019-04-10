package com.example.mjsongs.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mjsongs.Adapter.MJSongsListAdapter;
import com.example.mjsongs.R;
import com.example.mjsongs.model.GetSongsInteractorImpl;
import com.example.mjsongs.model.MJSongs;
import com.example.mjsongs.model.MainPresenterImpl;
import com.example.mjsongs.presenter.MainPresenter;
import com.example.mjsongs.presenter.OnItemClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView, OnItemClickListener {

    RecyclerView recyclerView;
    Toolbar toolbar;
    MainPresenter.Presenter presenter;
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        setSupportActionBar(toolbar);
        initProgressBar();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

//        if(!hasPermissions(this, PERMISSIONS)){
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }

        presenter = new MainPresenterImpl(this, new GetSongsInteractorImpl());
        //check internet is available or not
        if (isConnectingToInternet()) {
            presenter.requestDataFromServer();
        }
        else {
            Toast.makeText(this, "Unable to connect internet", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initProgressBar()
    {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setDataToRecyclerView(List<MJSongs> noticeArrayList) {
        MJSongsListAdapter mjSongsListAdapter=new MJSongsListAdapter(MainActivity.this,noticeArrayList,this);
        recyclerView.setAdapter(mjSongsListAdapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.showdetails(MainActivity.this,position);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
                return info.isConnected();
        }
        return false;
    }
}

