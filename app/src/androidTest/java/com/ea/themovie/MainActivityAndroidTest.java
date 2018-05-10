package com.ea.themovie;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;


import com.ea.themovie.util.RecyclerViewItemCountAssertion;
import com.ea.themovie.util.TestData;
import com.ea.themovie.util.ViewPagerIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static com.ea.themovie.util.TestUtil.atPosition;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidTest {

    Intent intent;

    @Rule
    public TestName testName = new TestName();

    @Rule
//    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            setupDataTest();
        }
    };

    @Before
    public void setup() {

    }

    @Test
    public void loadListPopularMovies_withRepositoryHavingMovies_checkTotalItem() {
        onView(allOf(
                withId(R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(20));

    }

    @Test
    public void loadListPopularMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem() {
        onView(allOf(
                withId(R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atPosition(0, hasDescendant(withText("Avengers: Infinity War")))));

    }

    @Test
    public void loadListPopularMovies_withError404_checkItemCountAfterActionRetry() {
        TestData.setTestId(TestData.TEST_DATA_ID_01);
        onView(allOf(
                withId(R.id.btn_retry),
                isCompletelyDisplayed()))
                .perform(ViewActions.click());

        onView(allOf(
                withId(R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(20));

    }

    @Test
    public void loadListMostRatedMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem() {
        ViewPager viewPager = mMainActivityRule.getActivity().findViewById(R.id.vp_home_page);
        IdlingRegistry.getInstance().register(new ViewPagerIdlingResource(viewPager, "vp"));

        onView(withId(R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        onView(allOf(
                withId(R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atPosition(0, hasDescendant(withText("Dilwale Dulhania Le Jayenge")))));

    }

    private void setupDataTest(){
        String methodName = testName.getMethodName();
        if (TextUtils.equals("loadListPopularMovies_withRepositoryHavingMovies_checkTotalItem", methodName)
                || TextUtils.equals("loadListPopularMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem", methodName)) {
            TestData.setTestId(TestData.TEST_DATA_ID_01);
        } else if (TextUtils.equals("loadListPopularMovies_withError404_checkItemCountAfterActionRetry", methodName)) {
            TestData.setTestId(TestData.TEST_DATA_ID_02);
        } else if (TextUtils.equals("loadListMostRatedMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem", methodName)) {
            TestData.setTestId(TestData.TEST_DATA_ID_03);
        }
    }
}
