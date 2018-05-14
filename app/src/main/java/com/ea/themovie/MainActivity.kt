package com.ea.themovie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ea.themovie.adapter.ViewPagerAdapter
import com.ea.themovie.entity.Movie
import com.ea.themovie.fragment.FavoriteMovieFragment
import com.ea.themovie.fragment.ListMovieFragment
import com.ea.themovie.fragment.MostRatedMovieFragment
import com.ea.themovie.fragment.PopularMovieFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private lateinit var adapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        adapter = ViewPagerAdapter(this, supportFragmentManager)
        adapter.addFragment(PopularMovieFragment(), R.string.home_popular)
        adapter.addFragment(MostRatedMovieFragment(), R.string.home_most_rated)
        adapter.addFragment(FavoriteMovieFragment(), R.string.home_my_favorite)
        vp_home_page.adapter = adapter

        view_pager_tab.setupWithViewPager(vp_home_page)
    }

    fun notifyDataNeighborTab(movie: Movie) {
        val position = vp_home_page.getCurrentItem()
        var fragment = adapter.getItemByPosition(position - 1) as? ListMovieFragment
        fragment?.refreshMoviesItem(movie, false)

        fragment = adapter.getItemByPosition(position + 1) as? ListMovieFragment
        fragment?.refreshMoviesItem(movie, false)
    }
}