package com.ea.themovie.presenter

import com.ea.themovie.entity.MovieVideo
import com.ea.themovie.entity.Video
import com.ea.themovie.repository.MovieRepository

class MovieDetailPresenter (private var movieRepository: MovieRepository?) : BasePresenter<MovieDetailPresenter.View>() {

    fun loadMovieTrailers(movieId : Int) {
        view?.prepareLoadingVideos()
        movieRepository?.getMovieVideos(
                movieId.toString(),
                object : MovieRepository.MovieRepositoryCallback<MovieVideo>{
                    override fun onSuccess(movieVideo: MovieVideo) {
                        if (movieVideo.videos.size > 0) {
                            view?.showVideoList(movieVideo.videos as ArrayList<Video>)
                        } else {
                            view?.showEmptyText()
                        }
                    }

                    override fun onError(error: Int, message: String) {
                        view?.showEmptyText()
                    }

                }
        );
    }

    interface View{
        fun prepareLoadingVideos()
        fun showVideoList(videos: ArrayList<Video>)
        fun showEmptyText()
    }
}