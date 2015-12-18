package com.tutorial.matt.popularmoviesapp.listeners;

import com.tutorial.matt.popularmoviesapp.models.Trailer;

import java.util.ArrayList;

/**
 * Created by matt on 12/14/15.
 */
public interface OnFetchMovieTrailersTaskCompleteListener {
    void onTaskCompleted(ArrayList<Trailer> reviews);
}
