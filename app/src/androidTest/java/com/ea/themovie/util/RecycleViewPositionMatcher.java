package com.ea.themovie.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecycleViewPositionMatcher extends TypeSafeMatcher<View>{
    private int position;
    private Matcher<View> itemMatcher;

    public RecycleViewPositionMatcher(int position, Matcher<View> itemMatcher) {
        super(RecyclerView.class);
        this.itemMatcher = itemMatcher;
        this.position = position;
    }
    @Override
    protected boolean matchesSafely(View view) {
        if (!(view instanceof RecyclerView)) {
            return false;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) {
            // has no item on such position
            return false;
        }
        return itemMatcher.matches(viewHolder.itemView);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("at RecycleView position: ");
        description.appendValue(position);
    }
}
