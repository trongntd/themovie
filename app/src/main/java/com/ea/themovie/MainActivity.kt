package com.ea.themovie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ea.themovie.adapter.HomePagerAdapter
import com.ea.themovie.entity.Movie
import com.ea.themovie.fragment.ListMovieFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private lateinit var adapter : HomePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        adapter = HomePagerAdapter(this, supportFragmentManager)
        vp_home_page.adapter = adapter

        view_pager_tab.setupWithViewPager(vp_home_page)
    }

    public fun notifyDataNeighborTab(movie: Movie) {
        val position = vp_home_page.getCurrentItem()
        var fragment: ListMovieFragment? = adapter.getItemByPosition(position - 1) as ListMovieFragment?
        if (fragment != null) {
            fragment.refreshMoviesItem(movie, false)
        }

        fragment = adapter.getItemByPosition(position + 1) as ListMovieFragment?
        if (fragment != null) {
            fragment.refreshMoviesItem(movie, false)
        }
    }

}