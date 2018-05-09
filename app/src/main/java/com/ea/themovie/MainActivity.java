package com.ea.themovie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ea.themovie.adapter.HomePagerAdapter;
import com.ea.themovie.entity.Movie;
import com.ea.themovie.fragment.ListMovieFragment;

public class MainActivity extends AppCompatActivity{
    ViewPager viewPager;
    HomePagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_home_page);
        adapter = new HomePagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.view_pager_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void notifyDataNeighborTab(Movie movie){
        int position = viewPager.getCurrentItem();
        ListMovieFragment fragment = (ListMovieFragment) adapter.getItemByPosition(position - 1);
        if (fragment != null) {
            fragment.refreshMoviesItem(movie, false);
        }

        fragment = (ListMovieFragment) adapter.getItemByPosition(position + 1);
        if (fragment != null) {
            fragment.refreshMoviesItem(movie, false);
        }
    }

}
