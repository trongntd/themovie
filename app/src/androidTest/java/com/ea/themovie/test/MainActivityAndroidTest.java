package com.ea.themovie.test;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.ea.themovie.MainActivity;
import com.ea.themovie.R;
import com.ea.themovie.util.AppViewAction;
import com.ea.themovie.util.RecyclerViewItemCountAssertion;
import com.ea.themovie.util.TestData;
import com.ea.themovie.util.ViewPagerIdlingResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ea.themovie.util.AppTestMatcher.atRecycleViewItemChild;
import static com.ea.themovie.util.AppTestMatcher.atRecycleViewPosition;
import static com.ea.themovie.util.AppTestMatcher.withSelected;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidTest {

    Intent intent;

    @Rule
    public TestName testName = new TestName();

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            setupDataTest();
        }
    };

    @Test
    public void loadListPopularMovies_withRepositoryHavingMovies_checkTotalItem() {
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(20));

    }

    @Test
    public void loadListPopularMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem() {
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atRecycleViewPosition(0, hasDescendant(withText("Avengers: Infinity War")))));

    }

    @Test
    public void loadListPopularMovies_withError404_checkItemCountAfterActionRetry() {
        TestData.setTestId(TestData.TEST_DATA_ID_01);
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.btn_retry),
                isCompletelyDisplayed()))
                .perform(ViewActions.click());

        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(20));

    }

    @Test
    public void loadListMostRatedMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem() {
        ViewPager viewPager = mMainActivityRule.getActivity().findViewById(com.ea.themovie.R.id.vp_home_page);
        IdlingRegistry.getInstance().register(new ViewPagerIdlingResource(viewPager, "loadListMostRatedMovies_withRepositoryHavingMovies_checkMovieTitleOfFirstItem"));

        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atRecycleViewPosition(0, hasDescendant(withText("Dilwale Dulhania Le Jayenge")))));

    }

    @Test
    public void listPopularMovies_toggleButtonFavorite_checkStateSelectIdButtonFavorite() {
        // Click to button favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, AppViewAction.clickChildViewWithId(com.ea.themovie.R.id.iv_favorite)));

        // Item un-favorite => my favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atRecycleViewItemChild(0, com.ea.themovie.R.id.iv_favorite, withSelected(true))));

        // Click to button favorite of the same item
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, AppViewAction.clickChildViewWithId(com.ea.themovie.R.id.iv_favorite)));

        // Item revert again from my favorite => un-favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atRecycleViewItemChild(0, com.ea.themovie.R.id.iv_favorite, withSelected(false))));
    }

    @Test
    public void listPopularMovies_toggleButtonFavorite_checkHasItemOnMyFavoriteList() {
        ViewPager viewPager = mMainActivityRule.getActivity().findViewById(com.ea.themovie.R.id.vp_home_page);
        IdlingRegistry.getInstance().register(new ViewPagerIdlingResource(viewPager, "listPopularMovies_toggleButtonFavorite_checkHasItemOnMyFavoriteList"));

        // Click to button favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, AppViewAction.clickChildViewWithId(com.ea.themovie.R.id.iv_favorite)));

        // swipe to most rated list
        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        // swipe to my favorite list
        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        // The first item movie has title is "Dilwale Dulhania Le Jayenge"
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atRecycleViewItemChild(0, com.ea.themovie.R.id.tv_title, withText("Avengers: Infinity War"))));
    }

    @Test
    public void listMostRatedMovies_toggleButtonFavorite_checkHasItemOnMyFavoriteList() {
        ViewPager viewPager = mMainActivityRule.getActivity().findViewById(com.ea.themovie.R.id.vp_home_page);
        IdlingRegistry.getInstance().register(new ViewPagerIdlingResource(viewPager, "listMostRatedMovies_toggleButtonFavorite_checkHasItemOnMyFavoriteList"));

        // swipe to most rated list
        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        // Click to button favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, AppViewAction.clickChildViewWithId(com.ea.themovie.R.id.iv_favorite)));

        // swipe to my favorite list
        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        // The first item movie has title is "Dilwale Dulhania Le Jayenge"
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(matches(atRecycleViewItemChild(0, com.ea.themovie.R.id.tv_title, withText("Dilwale Dulhania Le Jayenge"))));
    }

    @Test
    public void listPopularMovies_toggleButtonFavorite_checkRemoveItemOutOfFavoriteList() {
        ViewPager viewPager = mMainActivityRule.getActivity().findViewById(com.ea.themovie.R.id.vp_home_page);
        IdlingRegistry.getInstance().register(new ViewPagerIdlingResource(viewPager, "listPopularMovies_toggleButtonFavorite_checkRemoveItemOutOfFavoriteList"));

        // Click to button favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, AppViewAction.clickChildViewWithId(com.ea.themovie.R.id.iv_favorite)));

        // swipe to most rated list
        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        // swipe to my favorite list
        onView(ViewMatchers.withId(com.ea.themovie.R.id.vp_home_page))
                .perform(ViewActions.swipeLeft());

        // My favorite has 1 item
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(1));

        // Click to button favorite
        onView(allOf(
                ViewMatchers.withId(com.ea.themovie.R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, AppViewAction.clickChildViewWithId(com.ea.themovie.R.id.iv_favorite)));

        // List favorite is empty
        onView(allOf(
                ViewMatchers.withId(R.id.rv_list_movie),
                isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(0));
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
        } else if (TextUtils.equals("listPopularMovies_toggleButtonFavorite_checkStateSelectIdButtonFavorite", methodName)
                || TextUtils.equals("listPopularMovies_toggleButtonFavorite_checkRemoveItemOutOfFavoriteList", methodName)) {
            TestData.setTestId(TestData.TEST_DATA_ID_04);
        } else if (TextUtils.equals("listMostRatedMovies_toggleButtonFavorite_checkHasItemOnMyFavoriteList", methodName)) {
            TestData.setTestId(TestData.TEST_DATA_ID_05);
        }
    }
}
