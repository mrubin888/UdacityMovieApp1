package com.tutorial.matt.popularmoviesapp.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.data.MovieContract;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    private Context context = this;
    private boolean isFavorite;

    private ListView reviewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Lookup movie from sqlite
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Uri uri = MovieContract.MovieEntry.CONTENT_URI
                .buildUpon()
                .appendPath(id)
                .build();

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() != 1) {
            Log.w(TAG, "Fetched " + cursor.getCount() + " movies from SQLite (expected 1)");
        }
        cursor.moveToFirst();
        loadViewContentFromCursor(cursor);
        cursor.close();

//        new FetchMovieReviewsTask(this, movieReviewsTaskCompleteListener).execute(id);
//
//        reviewListView = (ListView) findViewById(R.id.movie_details_review_list);
//        reviewListView.setAdapter(new ReviewListAdapter(context));
    }

//    public OnFetchMovieReviewsTaskCompleteListener movieReviewsTaskCompleteListener = new OnFetchMovieReviewsTaskCompleteListener() {
//        @Override
//        public void onTaskCompleted(ArrayList<Review> reviews) {
//            Log.d(TAG, reviews.size() + " reviews found.");
//            TextView reviewHeaderView = (TextView) findViewById(R.id.movie_details_review_header);
//            reviewHeaderView.setText(reviews.size() + " reviews found.");
//            ReviewListAdapter reviewListAdapter = (ReviewListAdapter) reviewListView.getAdapter();
//            reviewListAdapter.setReviews(reviews);
//        }
//    };

    private void loadViewContentFromCursor (Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
        String posterPath = cursor.getString(cursor.getColumnIndex("poster_path"));
        String voteAverage = cursor.getString(cursor.getColumnIndex("vote_average"));
        String overview = cursor.getString(cursor.getColumnIndex("overview"));
        isFavorite = cursor.getInt(cursor.getColumnIndex("is_favorite")) != 0;

        ImageView imageView = (ImageView) findViewById(R.id.movie_details_poster_image);
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + posterPath)
                .into(imageView);

        TextView titleTextView = (TextView) findViewById(R.id.movie_details_title);
        titleTextView.setText(title);

        TextView releaseDateTextView = (TextView) findViewById(R.id.movie_details_release_date);
        releaseDateTextView.setText("Released " + releaseDate);

        TextView voteAverageTextView = (TextView) findViewById(R.id.movie_details_vote_average);
        voteAverageTextView.setText("Average Rating: " + voteAverage);

        TextView overviewTextView = (TextView) findViewById(R.id.movie_details_overview);
        overviewTextView.setText(overview);

        ImageView favoriteImageView = (ImageView) findViewById(R.id.movie_details_favorite_button);
        Picasso.with(this)
                .load(isFavorite ? R.drawable.favorite_filled : R.drawable.favorite_hollow)
                .into(favoriteImageView);
        favoriteImageView.setOnClickListener(onFavoriteClickListener);

        Button reviewsButton = (Button) findViewById(R.id.movie_details_reviews_button);
        reviewsButton.setOnClickListener(onReviewsClickListener);
    }

    private View.OnClickListener onFavoriteClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            String id = "" + intent.getStringExtra("id");
            Uri uri = MovieContract.MovieEntry.CONTENT_URI
                    .buildUpon()
                    .appendPath(id)
                    .build();

            ContentValues contentValues = new ContentValues();
            contentValues.put("is_favorite", !isFavorite);

            if(getContentResolver().update(uri, contentValues, null, null) != -1) {
                isFavorite = !isFavorite;
                ImageView favoriteImageView = (ImageView) findViewById(R.id.movie_details_favorite_button);
                Picasso.with(context)
                        .load(isFavorite ? R.drawable.favorite_filled : R.drawable.favorite_hollow)
                        .into(favoriteImageView);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private View.OnClickListener onReviewsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieReviewsActivity.class);
            intent.putExtra("movie_id", getIntent().getStringExtra("id"));

            startActivity(intent);
        }
    };
}
