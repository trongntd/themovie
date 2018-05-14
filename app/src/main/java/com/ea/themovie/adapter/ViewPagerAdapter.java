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
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mListFragments = new ArrayList<>();
    private List<Integer> mTitles = new ArrayList<>();
    private Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return mListFragments.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public void addFragment(Fragment fragment, int titleResId) {
        mListFragments.add(fragment);
        mTitles.add(titleResId);
    }

    public Fragment getItemByPosition(int position) {
        if (position >= 0 && position < getCount())
            return mListFragments.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(mTitles.get(position));
    }
}
