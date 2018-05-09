package com.trongntd.themovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trongntd.themovie.R;
import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ViewHolder>{
    private ArrayList<Movie> mList;
    private LayoutInflater inflater;

    public ListMovieAdapter(Context context, ArrayList<Movie> list){
        mList = (list != null)? list : new ArrayList<Movie>();
        inflater = LayoutInflater.from(context);
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mList.get(position);
        holder.tvTitle.setText(movie.title);
        holder.ivFavourite.setSelected(position%2 == 0);
        ImageUtil.loadImage(movie.posterPath, holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFavourite, ivPoster;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFavourite = itemView.findViewById(R.id.iv_favorite);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
