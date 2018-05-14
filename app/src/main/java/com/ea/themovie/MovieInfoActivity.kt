package com.ea.themovie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ea.themovie.entity.Movie
import com.ea.themovie.fragment.MovieInfoFragment

class MovieInfoActivity : AppCompatActivity(), MovieInfoFragment.OnMovieInfoChange {
    companion object {
        @JvmStatic val EX_MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        val movie : Movie = intent.getParcelableExtra(EX_MOVIE)
        val fragment = MovieInfoFragment.newInstance(movie)
        fragment.listener = this
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    override fun onFavoriteChange(movie: Movie) {
        val intent = Intent()
        intent.putExtra(EX_MOVIE, movie)
        setResult(Activity.RESULT_OK, intent)
    }

}