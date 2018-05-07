package com.trongntd.themovie.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {
    private static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/original";

    public static void loadImage(String url, ImageView imageView) {
        Glide.with(imageView)
                .asDrawable()
                .load(BASE_IMG_URL + url)
                .into(imageView);
    }
}
