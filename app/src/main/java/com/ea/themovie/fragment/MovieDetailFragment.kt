package com.ea.themovie.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MotionEvent
import android.view.View
import com.ea.themovie.R
import com.ea.themovie.YoutubePlayerActivity
import com.ea.themovie.adapter.VideoListAdapter
import com.ea.themovie.entity.Movie
import com.ea.themovie.entity.Video
import com.ea.themovie.presenter.MovieDetailPresenter
import com.ea.themovie.repository.MovieRepository
import com.ea.themovie.util.Constants
import com.ea.themovie.util.ImageUtil
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MovieDetailFragment : BaseFragment(), MovieDetailPresenter.View, VideoListAdapter.OnVideoItemClick {
    init {
        layoutResId = R.layout.fragment_movie_detail
    }

    lateinit var movieRepository: MovieRepository
        @Inject set

    private lateinit var presenter: MovieDetailPresenter

    private var adapter : VideoListAdapter? = null

    companion object {
        @JvmStatic val EX_MOVIE = "movie";

        @JvmStatic fun newInstance(movie : Movie): MovieDetailFragment {
            val movieDetailFragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(EX_MOVIE, movie)
            movieDetailFragment.arguments = bundle
            return movieDetailFragment
        }
    }

    private lateinit var thumbnailViewToLoaderMap: HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        thumbnailViewToLoaderMap = HashMap()
        application.movieComponent.inject(this)
        presenter = MovieDetailPresenter(movieRepository)
        presenter.attachView(this)

        gridMovieVideos.setOnTouchListener( object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                view.parent.requestDisallowInterceptTouchEvent(true)
                return false
            }

        })

        val movie = arguments?.getParcelable<Movie>(EX_MOVIE);
        if (movie != null) {
            bindingMovieData(movie)
        } else {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        adapter?.releaseLoader()
    }

    private fun bindingMovieData(movie: Movie) {
        ImageUtil.loadImage(movie.posterPath, ivMoviePoster)
        tvMovieTitle.text = movie.title
        tvMovieOverview.text = movie.overview
        tvMovieReleaseDate.text = formatReleaseDate(movie.releaseDate)
        ratingMovieVoteAverage.progress = movie.voteAverage.toInt()

        presenter.loadMovieTrailers(movie.id)

    }

    private fun formatReleaseDate(releaseDate : String) : String {
        val simpleDateFormat = SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.US)
        val date = simpleDateFormat.parse(releaseDate)
        simpleDateFormat.applyPattern(Constants.MOVIE_RELEASE_DATE_FORMAT)
        return simpleDateFormat.format(date)
    }

    override fun showVideoList(videos: ArrayList<Video>) {
        pbVideosLoading.visibility = View.GONE
        adapter = VideoListAdapter(activity, videos)
        adapter?.listener = this
        gridMovieVideos.layoutManager = VideoGridLayoutManager(activity)
        gridMovieVideos.adapter = adapter
    }

    override fun onItemClick(video: Video) {
        val intent = Intent(activity, YoutubePlayerActivity::class.java)
        intent.putExtra(YoutubePlayerActivity.EX_VIDEO_ID, video.key)
        startActivity(intent)
    }

    override fun showEmptyText() {
        pbVideosLoading.visibility = View.GONE
        tvNoTrailer.visibility = View.VISIBLE
    }

    override fun prepareLoadingVideos() {
        pbVideosLoading.visibility = View.VISIBLE
        tvNoTrailer.visibility = View.GONE
    }

    private class VideoGridLayoutManager(context: Context) : GridLayoutManager(context, 2) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }
}