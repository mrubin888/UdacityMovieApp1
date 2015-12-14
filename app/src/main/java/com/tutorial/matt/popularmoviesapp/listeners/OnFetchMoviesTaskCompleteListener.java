package com.tutorial.matt.popularmoviesapp.listeners;

import com.tutorial.matt.popularmoviesapp.models.Movie;

import java.util.ArrayList;

/**
 * Created by matt on 12/10/15.
 */
public interface OnFetchMoviesTaskCompleteListener {
    void onTaskCompleted(ArrayList<Movie> movies);
}
