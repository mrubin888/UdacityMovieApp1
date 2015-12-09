package com.tutorial.matt.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.movie_details_poster_image);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + intent
                .getStringExtra("poster_path"))
                .into(imageView);

        TextView titleTextView = (TextView) findViewById(R.id.movie_details_title);
        titleTextView.setText(intent.getStringExtra("title"));

        TextView releaseDateTextView = (TextView) findViewById(R.id.movie_details_release_date);
        releaseDateTextView.setText("Released " + intent.getStringExtra("release_date"));

        TextView voteAverageTextView = (TextView) findViewById(R.id.movie_details_vote_average);
        voteAverageTextView.setText("Average Rating: " + intent.getStringExtra("vote_average"));

        TextView overviewTextView = (TextView) findViewById(R.id.movie_details_overview);
        overviewTextView.setText(intent.getStringExtra("overview"));

    }
}
