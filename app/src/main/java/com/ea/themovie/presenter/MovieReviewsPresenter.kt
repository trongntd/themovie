package com.ea.themovie.presenter

import com.ea.themovie.entity.MovieReviews
import com.ea.themovie.entity.Review
import com.ea.themovie.repository.MovieRepository

class MovieReviewsPresenter(private var movieRepository: MovieRepository) : BasePresenter<MovieReviewsPresenter.View>(){

    fun getMovieReviews(movieId : Int) {
        view?.showLoading()
        movieRepository.getMovieReviews(movieId.toString(), object : MovieRepository.MovieRepositoryCallback<MovieReviews>{
            override fun onSuccess(movieReviews: MovieReviews?) {
                view?.hideLoading()
                if (movieReviews != null) {
                    if (movieReviews.reviews.isEmpty()) {
                        view?.showEmpty()
                    } else {
                        view?.showListReviews(movieReviews.reviews)
                    }
                }
            }

            override fun onError(error: Int, message: String?) {
                view?.hideLoading()
                view?.showError(message)
            }

        })
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showListReviews(reviews : List<Review>)
        fun showEmpty()
        fun showError(message : String?)
    }
}