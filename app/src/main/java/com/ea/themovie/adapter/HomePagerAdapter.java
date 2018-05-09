package com.ea.themovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ea.themovie.R;
import com.ea.themovie.fragment.FavoriteMovieFragment;
import com.ea.themovie.fragment.MostRatedMovieFragment;
import com.ea.themovie.fragment.PopularMovieFragment;

import java.lang.reflect.Constructor;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private String[] mList = {
            PopularMovieFragment.class.getName(),
            MostRatedMovieFragment.class.getName(),
            FavoriteMovieFragment.class.getName()
    };
    private int[] mTitle = {R.string.home_popular, R.string.home_most_rated, R.string.home_my_favorite};
    private Context mContext;
    private SparseArray<Fragment> mMapCache = new SparseArray<>();

    public HomePagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            Class<?> fragmentClass = Class.forName(mList[position]);
            Constructor<?> ctr = fragmentClass.getConstructor();
            Fragment fragment = (Fragment) ctr.newInstance();
            mMapCache.put(position, fragment);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mMapCache.remove(position);
    }

    public Fragment getItemByPosition(int position) {
        return mMapCache.get(position);
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
