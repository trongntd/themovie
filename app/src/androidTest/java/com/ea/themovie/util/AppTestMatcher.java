package com.ea.themovie.util;

import android.support.annotation.NonNull;
import android.view.View;

import org.hamcrest.Matcher;

public class AppTestMatcher {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> withSelected(final boolean isSelected) {
        return new StateSelectedMatcher(isSelected);
    }

    public static Matcher<View> atRecycleViewPosition(final int position, @NonNull final Matcher<View> itemMatcher)  {
        return new RecycleViewPositionMatcher(position, itemMatcher);
    }

    public static Matcher<View> atRecycleViewItemChild(final int position, final int childId, @NonNull final Matcher<View> itemMatcher)  {
        return new RecycleViewChildItemMatcher(position, childId, itemMatcher);
    }


}
