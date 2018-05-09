package com.ea.themovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ea.themovie.R;
import com.ea.themovie.entity.Movie;
import com.ea.themovie.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ViewHolder>
        implements View.OnClickListener{
    private ArrayList<Movie> mList;
    private LayoutInflater inflater;
    private OnItemMovieClick listener;

    public ListMovieAdapter(Context context, ArrayList<Movie> list){
        mList = (list != null)? list : new ArrayList<Movie>();
        inflater = LayoutInflater.from(context);
    }

    public void setListener(OnItemMovieClick listener) {
        this.listener = listener;
    }

    public void setData(List<Movie> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void addData(List<Movie> list) {
        mList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_movie_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.ivFavourite.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mList.get(position);
        holder.tvTitle.setText(movie.title);
        holder.ivFavourite.setSelected(movie.isFavorite);
        holder.ivFavourite.setTag(movie);
        ImageUtil.loadImage(movie.posterPath, holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_favorite) {
            if (listener != null) {
                listener.onFavoriteToggle((Movie) view.getTag());
            }
        }
    }

    public void notifyDataItemChanged(Movie movie) {
        int pos = mList.indexOf(movie);
        if (pos >= 0 && pos < getItemCount()) {
            Movie m = mList.get(pos);
            mList.set(pos, movie);
            notifyItemChanged(pos);
        }
    }

    public void removeItem(Movie movie) {
        mList.remove(movie);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFavourite, ivPoster;
        TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            ivFavourite = itemView.findViewById(R.id.iv_favorite);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemMovieClick{
        void onFavoriteToggle(Movie movie);
    }
}
