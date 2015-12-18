package com.tutorial.matt.popularmoviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.adapters.ReviewListAdapter;
import com.tutorial.matt.popularmoviesapp.listeners.OnFetchMovieReviewsTaskCompleteListener;
import com.tutorial.matt.popularmoviesapp.models.Review;
import com.tutorial.matt.popularmoviesapp.tasks.FetchMovieReviewsTask;

import java.util.ArrayList;

/**
 * Created by matt on 12/14/15.
 */
public class MovieReviewsActivity extends AppCompatActivity {

    private static final String TAG = MovieReviewsActivity.class.getSimpleName();

    private Context context = this;
    private ListView reviewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movie_id");

        new FetchMovieReviewsTask(this, movieReviewsTaskCompleteListener).execute(movieId);
        reviewListView = (ListView) findViewById(R.id.movie_reviews_list);
        reviewListView.setAdapter(new ReviewListAdapter(context));
    }

    public OnFetchMovieReviewsTaskCompleteListener movieReviewsTaskCompleteListener = new OnFetchMovieReviewsTaskCompleteListener() {
        @Override
        public void onTaskCompleted(ArrayList<Review> reviews) {
            Log.d(TAG, reviews.size() + " reviews found.");
            TextView reviewHeaderView = (TextView) findViewById(R.id.movie_reviews_header);
            reviewHeaderView.setText(reviews.size() + " review" + (reviews.size() != 1 ? "s" : "") + " found.");
            ReviewListAdapter reviewListAdapter = (ReviewListAdapter) reviewListView.getAdapter();
            reviewListAdapter.setReviews(reviews);
        }
    };
}
