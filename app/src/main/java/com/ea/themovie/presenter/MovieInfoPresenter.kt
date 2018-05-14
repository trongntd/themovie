package com.ea.themovie.presenter

import com.ea.themovie.entity.Movie
import com.ea.themovie.repository.MovieRepository

class MovieInfoPresenter(private var movieRepository: MovieRepository) : BasePresenter<MovieInfoPresenter.View>() {

    fun favoriteToggle(movie: Movie) {
        if (movie.isFavorite) {
            movie.isFavorite = false
            movieRepository.removeFromFavorite(movie)
        } else {
            movie.isFavorite = true
            movieRepository.addToFavorite(movie)
        }
        if (view != null) {
            view.refreshFavoriteStatus(movie)
        }
    }
    interface View {
        fun refreshFavoriteStatus(movie : Movie)
    }
}