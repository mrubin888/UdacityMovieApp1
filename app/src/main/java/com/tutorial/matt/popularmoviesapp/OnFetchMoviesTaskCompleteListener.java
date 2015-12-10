package com.tutorial.matt.popularmoviesapp;

import java.util.ArrayList;

/**
 * Created by matt on 12/10/15.
 */
public interface OnFetchMoviesTaskCompleteListener {
    void onTaskCompleted(ArrayList<Movie> movies);
}
