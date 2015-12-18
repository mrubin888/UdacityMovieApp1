package com.tutorial.matt.popularmoviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.adapters.TrailerListAdapter;
import com.tutorial.matt.popularmoviesapp.listeners.OnFetchMovieTrailersTaskCompleteListener;
import com.tutorial.matt.popularmoviesapp.models.Trailer;
import com.tutorial.matt.popularmoviesapp.tasks.FetchMovieTrailersTask;

import java.util.ArrayList;

/**
 * Created by matt on 12/14/15.
 */
public class MovieTrailersActivity extends AppCompatActivity {

    private static final String TAG = MovieTrailersActivity.class.getSimpleName();

    private Context context = this;
    private ListView trailerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailers);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movie_id");

        new FetchMovieTrailersTask(this, movieTrailersTaskCompleteListener).execute(movieId);
        trailerListView = (ListView) findViewById(R.id.movie_trailers_list);
        trailerListView.setAdapter(new TrailerListAdapter(context));
        trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrailerListAdapter trailerListAdapter = (TrailerListAdapter) trailerListView.getAdapter();
                Trailer trailer = (Trailer) trailerListAdapter.getItem(position);
                String url = trailer.getUrl();
                Log.d(TAG, "onItemClick: " + url);

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    public OnFetchMovieTrailersTaskCompleteListener movieTrailersTaskCompleteListener = new OnFetchMovieTrailersTaskCompleteListener() {
        @Override
        public void onTaskCompleted(ArrayList<Trailer> trailers) {
            Log.d(TAG, trailers.size() + " trailers found.");
            TextView reviewHeaderView = (TextView) findViewById(R.id.movie_trailers_header);
            reviewHeaderView.setText(trailers.size() + " trailer" + (trailers.size() != 1 ? "s" : "") + " found.");
            TrailerListAdapter trailerListAdapter = (TrailerListAdapter) trailerListView.getAdapter();
            trailerListAdapter.setTrailers(trailers);
        }
    };
}
