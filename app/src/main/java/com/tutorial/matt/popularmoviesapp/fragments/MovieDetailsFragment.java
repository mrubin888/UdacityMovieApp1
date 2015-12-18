package com.tutorial.matt.popularmoviesapp.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.activities.MovieReviewsActivity;
import com.tutorial.matt.popularmoviesapp.activities.MovieTrailersActivity;
import com.tutorial.matt.popularmoviesapp.data.MovieContract;

/**
 * Created by matt on 12/17/15.
 */
public class MovieDetailsFragment extends Fragment {

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private Context context;

    private boolean isFavorite = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args == null) {
            return new View(getActivity());
        }

        // Lookup movie from sqlite
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        context = getActivity();

        String id = args.getString("id");

        Log.d(TAG, "onCreateView: " + id);
        Uri uri = MovieContract.MovieEntry.CONTENT_URI
                .buildUpon()
                .appendPath(id)
                .build();

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() != 1) {
            Log.w(TAG, "Fetched " + cursor.getCount() + " movies from SQLite (expected 1)");
        }
        else {
            cursor.moveToFirst();
            loadViewContentFromCursor(rootView, cursor);
            cursor.close();

            Log.d(TAG, "onCreateView: COMPLETE");
        }
        
        return rootView;
    }

    private void loadViewContentFromCursor (View rootView, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
        String posterPath = cursor.getString(cursor.getColumnIndex("poster_path"));
        String voteAverage = cursor.getString(cursor.getColumnIndex("vote_average"));
        String overview = cursor.getString(cursor.getColumnIndex("overview"));
        isFavorite = cursor.getInt(cursor.getColumnIndex("is_favorite")) != 0;

        ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_details_poster_image);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + posterPath)
                .into(imageView);

        TextView titleTextView = (TextView) rootView.findViewById(R.id.movie_details_title);
        titleTextView.setText(title);

        TextView releaseDateTextView = (TextView) rootView.findViewById(R.id.movie_details_release_date);
        releaseDateTextView.setText("Released " + releaseDate);

        TextView voteAverageTextView = (TextView) rootView.findViewById(R.id.movie_details_vote_average);
        voteAverageTextView.setText("Average Rating: " + voteAverage);

        TextView overviewTextView = (TextView) rootView.findViewById(R.id.movie_details_overview);
        overviewTextView.setText(overview);

        ImageView favoriteImageView = (ImageView) rootView.findViewById(R.id.movie_details_favorite_button);
        Picasso.with(context)
                .load(isFavorite ? R.drawable.favorite_filled : R.drawable.favorite_hollow)
                .into(favoriteImageView);
        favoriteImageView.setOnClickListener(onFavoriteClickListener);

        Button trailersButton = (Button) rootView.findViewById(R.id.movie_details_trailers_button);
        trailersButton.setOnClickListener(onTrailersClickListener);

        Button reviewsButton = (Button) rootView.findViewById(R.id.movie_details_reviews_button);
        reviewsButton.setOnClickListener(onReviewsClickListener);
    }

    private View.OnClickListener onFavoriteClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String id = getArguments().getString("id");
            Uri uri = MovieContract.MovieEntry.CONTENT_URI
                    .buildUpon()
                    .appendPath(id)
                    .build();

            ContentValues contentValues = new ContentValues();
            contentValues.put("is_favorite", !isFavorite);

            if(context.getContentResolver().update(uri, contentValues, null, null) != -1) {
                isFavorite = !isFavorite;
                ImageView favoriteImageView = (ImageView) getView().findViewById(R.id.movie_details_favorite_button);
                Picasso.with(context)
                        .load(isFavorite ? R.drawable.favorite_filled : R.drawable.favorite_hollow)
                        .into(favoriteImageView);
            }
        }
    };

    private View.OnClickListener onTrailersClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieTrailersActivity.class);
            intent.putExtra("movie_id", getArguments().getString("id"));

            startActivity(intent);
        }
    };

    private View.OnClickListener onReviewsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieReviewsActivity.class);
            intent.putExtra("movie_id", getArguments().getString("id"));

            startActivity(intent);
        }
    };
}
