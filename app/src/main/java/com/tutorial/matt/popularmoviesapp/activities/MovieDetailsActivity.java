package com.tutorial.matt.popularmoviesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putString("id", getIntent().getStringExtra("id"));

            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            movieDetailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, movieDetailsFragment)
                    .commit();
        }
    }
}
