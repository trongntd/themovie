package com.ea.themovie.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecycleViewChildItemMatcher extends TypeSafeMatcher<View>{
    private int position;
    private int childId;
    private Matcher<View> itemMatcher;

    public RecycleViewChildItemMatcher(int position, int childId, Matcher<View> itemMatcher) {
        super(RecyclerView.class);
        this.itemMatcher = itemMatcher;
        this.position = position;
        this.childId = childId;
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

        View childView = viewHolder.itemView.findViewById(childId);
        if (childView == null) {
            // has no item child
            return false;
        }

        return itemMatcher.matches(childView);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("at RecycleView get child : ");
        description.appendValue(childId);
        description.appendText(" of item at: ");
        description.appendValue(position);
    }
}
