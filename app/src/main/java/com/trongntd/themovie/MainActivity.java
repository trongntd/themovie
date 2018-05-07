package com.trongntd.themovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.presenter.ListMoviePresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    ListMoviePresenter listMoviePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
