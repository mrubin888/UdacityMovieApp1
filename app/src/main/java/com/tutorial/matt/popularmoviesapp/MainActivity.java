package com.tutorial.matt.popularmoviesapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Activity context;
    private Spinner spinner;
    private Button submitButton;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        new FetchMoviesTask(this, fetchMoviesTaskCompleteListener).execute(getResources().getStringArray(R.array.sort_by_array)[0]);

        spinner = (Spinner) findViewById(R.id.sort_by_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchMoviesTask(context, fetchMoviesTaskCompleteListener).execute((String) spinner.getSelectedItem());
            }
        });
        gridView = (GridView) findViewById(R.id.poster_grid);
    }

    private OnFetchMoviesTaskCompleteListener fetchMoviesTaskCompleteListener = new OnFetchMoviesTaskCompleteListener() {
        @Override
        public void onTaskCompleted(final ArrayList<Movie> movies) {
            gridView.setAdapter(new MovieListAdapter(context, movies));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie clickedMovie = movies.get(position);

                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("title", clickedMovie.getTitle());
                    intent.putExtra("release_date", clickedMovie.getReleaseDate());
                    intent.putExtra("poster_path", clickedMovie.getPosterPath());
                    intent.putExtra("vote_average", clickedMovie.getVoteAverage());
                    intent.putExtra("overview", clickedMovie.getOverview());

                    startActivity(intent);
                }
            });
        }
    };
}
