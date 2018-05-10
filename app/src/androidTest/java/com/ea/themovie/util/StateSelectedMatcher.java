package com.ea.themovie.util;

import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class StateSelectedMatcher extends TypeSafeMatcher<View>{
    private boolean expected;

    public StateSelectedMatcher(boolean isSelected) {
        super(View.class);
        this.expected = isSelected;
    }

    @Override
    protected boolean matchesSafely(View view) {
        return expected == view.isSelected();
    }



    @Override
    public void describeTo(Description description) {
        description.appendText("with state selected: ");
        description.appendValue(expected);
    }
}
