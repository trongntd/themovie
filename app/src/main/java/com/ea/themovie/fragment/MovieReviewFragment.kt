package com.ea.themovie.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import com.ea.themovie.R
import com.ea.themovie.adapter.ReviewListAdapter
import com.ea.themovie.entity.Movie
import com.ea.themovie.entity.Review
import com.ea.themovie.presenter.MovieReviewsPresenter
import kotlinx.android.synthetic.main.fragment_movie_review.*
import android.content.Intent
import android.net.Uri
import com.ea.themovie.repository.MovieRepository
import com.google.android.youtube.player.internal.m
import com.google.android.youtube.player.internal.p
import javax.inject.Inject


class MovieReviewFragment : BaseFragment(), MovieReviewsPresenter.View, ReviewListAdapter.OnReviewItemClick{
    init {
        layoutResId = R.layout.fragment_movie_review
    }

    companion object {
        @JvmStatic val EX_MOVIE = "movie";

        @JvmStatic fun newInstance(movie : Movie): MovieReviewFragment {
            val movieReviewFragment = MovieReviewFragment()
            val bundle = Bundle()
            bundle.putParcelable(EX_MOVIE, movie)
            movieReviewFragment.arguments = bundle
            return movieReviewFragment
        }
    }

    private lateinit var adapter : ReviewListAdapter
    lateinit var presenter : MovieReviewsPresenter
    lateinit var movieRepository: MovieRepository
        @Inject set

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        application.movieComponent.inject(this)
        presenter = MovieReviewsPresenter(movieRepository)
        presenter.attachView(this)

        adapter = ReviewListAdapter(activity, this)
        rvReviews.adapter = adapter
        rvReviews.layoutManager = LinearLayoutManager(activity)

        val movie = arguments?.getParcelable<Movie>(EX_MOVIE);
        if (movie != null) {
            presenter.getMovieReviews(movie.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onReadMoreClick(review: Review) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(review.url)
        startActivity(i)
    }

    override fun showLoading() {
        pbReviewLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbReviewLoading.visibility = View.GONE
    }

    override fun showListReviews(reviews: List<Review>) {
        adapter.setData(reviews)
        adapter.notifyDataSetChanged()
    }

    override fun showEmpty() {
        tvNoReview.visibility = View.VISIBLE
    }

    override fun showError(message: String?) {
        var mess = message
        if (mess == null) {
            mess = getString(R.string.api_unknown_error)
        }
        Toast.makeText(activity, mess, Toast.LENGTH_LONG).show()
    }
}