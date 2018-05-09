package com.ea.themovie.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestData {
    private static int mCurrentTestId = 0;

    public static final int TEST_DATA_ID_01 = 1;
    public static final int TEST_DATA_ID_02 = 2;
    public static final int TEST_DATA_ID_03 = 3;

    private static SparseArray<String> movieApiData = new SparseArray<>();
    static {
        movieApiData.put(TEST_DATA_ID_01,"api_data_popular_movies.json");
        movieApiData.put(TEST_DATA_ID_03,"api_data_most_rated_movies.json");
    }

    public static String getMovieApiData(int testId) {
        String file = movieApiData.get(testId);
        if (file != null) {
            return getStringFromResource(file);
        }
        return null;
    }

    public static void setTestId(int testId){
        mCurrentTestId = testId;
    }

    public static int getTestId() {
        return mCurrentTestId;
    }

    private static String getStringFromResource(String filename) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream is = TestData.class.getClassLoader().getResourceAsStream(filename);
            reader = new BufferedReader( new InputStreamReader(is, "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
