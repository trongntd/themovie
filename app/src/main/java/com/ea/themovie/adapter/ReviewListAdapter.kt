package com.ea.themovie.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ea.themovie.R
import com.ea.themovie.entity.Review

class ReviewListAdapter(private val context : Context, private val listener : OnReviewItemClick)
    : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>(){
    private val mList : ArrayList<Review>
    init {
        mList = ArrayList<Review>()
    }

    fun setData(reviews : List<Review>) {
        mList.clear()
        mList.addAll(reviews)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false)
        val holder = ViewHolder(view)
        holder.btnReviewReadMore.setOnClickListener({
            listener?.onReadMoreClick(it.getTag() as Review)
        })
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = mList.get(position)
        holder.tvReviewAuthor.text = review.author
        holder.tvReviewContent.text = review.content
        holder.btnReviewReadMore.setTag(review)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvReviewAuthor : TextView
        val tvReviewContent : TextView
        val btnReviewReadMore : TextView
        init {
            tvReviewAuthor = itemView.findViewById(R.id.tvReviewAuthor)
            tvReviewContent = itemView.findViewById(R.id.tvReviewContent)
            btnReviewReadMore = itemView.findViewById(R.id.btnReviewReadMore)
        }
    }

    interface OnReviewItemClick {
        fun onReadMoreClick(review: Review)
    }
}