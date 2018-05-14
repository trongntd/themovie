package com.ea.themovie.fragment

import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide.init
import com.ea.themovie.R
import com.ea.themovie.adapter.ViewPagerAdapter
import com.ea.themovie.entity.Movie
import com.ea.themovie.presenter.MovieInfoPresenter
import com.ea.themovie.repository.MovieRepository
import kotlinx.android.synthetic.main.fragment_movie_info.*
import javax.inject.Inject

class MovieInfoFragment : BaseFragment(), MovieInfoPresenter.View {
    init {
        layoutResId = R.layout.fragment_movie_info
    }

    companion object {
        @JvmStatic val EX_MOVIE = "movie";

        @JvmStatic fun newInstance(movie : Movie): MovieInfoFragment {
            val movieInfoFragment = MovieInfoFragment()
            val bundle = Bundle()
            bundle.putParcelable(EX_MOVIE, movie)
            movieInfoFragment.arguments = bundle
            return movieInfoFragment
        }
    }

    private lateinit var adapter : ViewPagerAdapter
    lateinit var movieRepository: MovieRepository
        @Inject set
    lateinit var presenter: MovieInfoPresenter
    var listener : OnMovieInfoChange? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        application.movieComponent.inject(this)
        presenter = MovieInfoPresenter(movieRepository)
        presenter.attachView(this)

        val movie = arguments?.getParcelable<Movie>(EX_MOVIE);
        if (movie != null) {
            adapter = ViewPagerAdapter(activity, fragmentManager)
            adapter.addFragment(MovieDetailFragment.newInstance(movie), R.string.movie_detail)
            adapter.addFragment(MovieReviewFragment.newInstance(movie), R.string.movie_review)

            vpMovieInfo.adapter = adapter
            tabMovieInfo.setupWithViewPager(vpMovieInfo)
            ivMovieFavorite.isSelected = movie.isFavorite

            ivMovieFavorite.setOnClickListener({
                presenter.favoriteToggle(movie)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun refreshFavoriteStatus(movie: Movie) {
        ivMovieFavorite.isSelected = movie.isFavorite
        listener?.onFavoriteChange(movie)
    }

    interface OnMovieInfoChange {
        fun onFavoriteChange(movie: Movie)
    }
}