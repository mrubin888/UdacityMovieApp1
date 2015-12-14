package com.tutorial.matt.popularmoviesapp.listeners;

import com.tutorial.matt.popularmoviesapp.models.Review;

import java.util.ArrayList;

/**
 * Created by matt on 12/14/15.
 */
public interface OnFetchMovieReviewsTaskCompleteListener {
    void onTaskCompleted(ArrayList<Review> reviews);
}
