package com.ea.themovie.util;

import com.ea.themovie.entity.Movie;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TestUtil {
    public static Matcher withMovieTitle(final Matcher nameMatcher){
        return new TypeSafeMatcher<Movie>(){
            @Override
            public boolean matchesSafely(Movie movie) {
                return nameMatcher.matches(movie.title);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
