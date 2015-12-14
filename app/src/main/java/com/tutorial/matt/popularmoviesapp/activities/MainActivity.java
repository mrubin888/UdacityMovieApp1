package com.tutorial.matt.popularmoviesapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.tutorial.matt.popularmoviesapp.adapters.MovieListAdapter;
import com.tutorial.matt.popularmoviesapp.listeners.OnFetchMoviesTaskCompleteListener;
import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.data.MovieContract;
import com.tutorial.matt.popularmoviesapp.models.Movie;
import com.tutorial.matt.popularmoviesapp.tasks.FetchMoviesTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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
                String selection = spinner.getSelectedItem().toString();
                String[] spinnerOptions = getResources().getStringArray(R.array.sort_by_array);

                if (spinnerOptions[2].equals(selection)) { // Favorites)
                    Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, "is_favorite", null, null);
                    cursor.moveToFirst();
                    final ArrayList<Movie> movies = new ArrayList<Movie>();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        movies.add(new Movie(cursor));
                        cursor.moveToNext();
                    }
                    cursor.close();

                    MovieListAdapter movieListAdapter = (MovieListAdapter) gridView.getAdapter();
                    movieListAdapter.setMovies(movies);
                } else {
                    new FetchMoviesTask(context, fetchMoviesTaskCompleteListener).execute((String) spinner.getSelectedItem());
                }
            }
        });

        gridView = (GridView) findViewById(R.id.poster_grid);
        gridView.setAdapter(new MovieListAdapter(context));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieListAdapter adapter = (MovieListAdapter) parent.getAdapter();
                Movie clickedMovie = (Movie) adapter.getItem(position);

                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("id", clickedMovie.getId());

                startActivity(intent);
            }
        });
    }

    private OnFetchMoviesTaskCompleteListener fetchMoviesTaskCompleteListener = new OnFetchMoviesTaskCompleteListener() {
        @Override
        public void onTaskCompleted(ArrayList<Movie> movies) {
            MovieListAdapter movieListAdapter = (MovieListAdapter) gridView.getAdapter();
            movieListAdapter.setMovies(movies);
        }
    };
}