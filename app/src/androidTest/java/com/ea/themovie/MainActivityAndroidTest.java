package com.ea.themovie;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.trongntd.themovie.MainActivity;
import com.trongntd.themovie.R;
import com.trongntd.themovie.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loadListMovie_withRepositoryHavingMovies_callShowListMovie() {
        onView(withId(R.id.rv_list_movie)).check(new RecyclerViewItemCountAssertion(Matchers.greaterThan(1)));
    }
}
