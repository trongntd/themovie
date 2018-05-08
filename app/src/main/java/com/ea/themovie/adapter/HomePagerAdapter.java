package com.ea.themovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ea.themovie.R;
import com.ea.themovie.fragment.MostRatedMovieFragment;
import com.ea.themovie.fragment.PopularMovieFragment;

import java.lang.reflect.Constructor;
import java.util.List;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private String[] mList = {PopularMovieFragment.class.getName(), MostRatedMovieFragment.class.getName()};
    private int[] mTitle = {R.string.home_popular, R.string.home_most_rated, R.string.home_my_favorite};
    private Context mContext;

    public HomePagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            Class<?> fragmentClass = Class.forName(mList[position]);
            Constructor<?> ctr = fragmentClass.getConstructor();
            return (Fragment) ctr.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(mTitle[position]);
    }
}
