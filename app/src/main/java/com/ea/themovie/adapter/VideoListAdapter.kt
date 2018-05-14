package com.ea.themovie.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ea.themovie.R
import com.ea.themovie.entity.Movie
import com.ea.themovie.entity.Video
import com.ea.themovie.util.Constants
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailView
import java.util.ArrayList
import com.google.android.youtube.player.YouTubeThumbnailLoader

class VideoListAdapter(context: Context, list: ArrayList<Video>?) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    var mList : ArrayList<Video>
    var inflater : LayoutInflater
    private var thumbnailViewToLoaderMap: HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>
    var listener : OnVideoItemClick? = null

    init {
        mList = list?: ArrayList<Video>()

        inflater = LayoutInflater.from(context)
        thumbnailViewToLoaderMap = HashMap()
    }

    fun setData(videos : List<Video>) {
        releaseLoader()
        mList.clear()
        mList.addAll(videos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.video_list_item, parent, false);
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener( {
            val video = it.getTag() as? Video?
            if (video != null) {
                listener?.onItemClick(video)
            }
        })
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = mList.get(position)
        val loader = thumbnailViewToLoaderMap.get(holder.youtubeThumnailView)
        if (loader == null) {
            holder.youtubeThumnailView.setTag(video.key)
            holder.youtubeThumnailView.initialize(Constants.GOOGLE_API_KEY, ThumbnailListener(holder))
        } else {
            loader.setVideo(video.key)
        }
        holder.ivPlayVideo.visibility = View.GONE
        holder.tvVideoTitle.text = video.name
        holder.itemView.setTag(video)
    }

    fun releaseLoader() {
        thumbnailViewToLoaderMap.forEach{
            it.value.release()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var youtubeThumnailView: YouTubeThumbnailView
        var ivPlayVideo : ImageView
        var tvVideoTitle : TextView

        init {
            youtubeThumnailView = itemView.findViewById(R.id.youTubeThumbnailView)
            ivPlayVideo = itemView.findViewById(R.id.ivPlayVideo)
            tvVideoTitle = itemView.findViewById(R.id.tvVideoTitle)
        }
    }

    private inner class ThumbnailListener(holder: ViewHolder) : YouTubeThumbnailView.OnInitializedListener, YouTubeThumbnailLoader.OnThumbnailLoadedListener {
        var holder : ViewHolder
        init {
            this.holder = holder
        }

        override fun onInitializationSuccess(view: YouTubeThumbnailView, loader: YouTubeThumbnailLoader) {
            thumbnailViewToLoaderMap.put(view, loader)
            loader.setOnThumbnailLoadedListener(this)
            val key = view.getTag() as? String
            if (key != null) {
                 loader.setVideo(key)
            }
        }

        override fun onInitializationFailure(view: YouTubeThumbnailView, result: YouTubeInitializationResult) {
            view.setImageResource(R.mipmap.no_thumbnail);
            holder.ivPlayVideo.visibility = View.GONE
        }

        override fun onThumbnailLoaded(view: YouTubeThumbnailView, videoId: String) {
            holder.ivPlayVideo.visibility = View.VISIBLE
        }

        override fun onThumbnailError(view: YouTubeThumbnailView, error: YouTubeThumbnailLoader.ErrorReason) {
            view.setImageResource(R.mipmap.no_thumbnail);
            holder.ivPlayVideo.visibility = View.GONE
        }

    }

    interface OnVideoItemClick{
        fun onItemClick(video: Video)
    }
}