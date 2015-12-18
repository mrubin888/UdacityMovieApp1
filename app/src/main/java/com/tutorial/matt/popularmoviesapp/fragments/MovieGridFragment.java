package com.tutorial.matt.popularmoviesapp.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.adapters.MovieListAdapter;
import com.tutorial.matt.popularmoviesapp.data.MovieContract;
import com.tutorial.matt.popularmoviesapp.listeners.OnFetchMoviesTaskCompleteListener;
import com.tutorial.matt.popularmoviesapp.models.Movie;
import com.tutorial.matt.popularmoviesapp.tasks.FetchMoviesTask;

import java.util.ArrayList;

/**
 * Created by matt on 12/14/15.
 */
public class MovieGridFragment extends Fragment {

    private static final String TAG = MovieGridFragment.class.getSimpleName();

    private Context context;

    private Spinner spinner;
    private Button submitButton;

    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        context = getActivity();
        View rootView = inflater.inflate(R.layout.grid_fragment, container, false);

        new FetchMoviesTask(context, fetchMoviesTaskCompleteListener).execute(getResources().getStringArray(R.array.sort_by_array)[0]);

        spinner = (Spinner) rootView.findViewById(R.id.sort_by_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        submitButton = (Button) rootView.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = spinner.getSelectedItem().toString();
                String[] spinnerOptions = getResources().getStringArray(R.array.sort_by_array);

                if (spinnerOptions[2].equals(selection)) { // Favorites)
                    Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, "is_favorite", null, null);
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

        gridView = (GridView) rootView.findViewById(R.id.poster_grid);
        gridView.setAdapter(new MovieListAdapter(context));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieListAdapter adapter = (MovieListAdapter) parent.getAdapter();
                Movie clickedMovie = (Movie) adapter.getItem(position);

                Callback callback = (Callback) getActivity();
                callback.onItemSelected(clickedMovie.getId());
//
//                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
//                intent.putExtra("id", clickedMovie.getId());
//
//                startActivity(intent);
            }
        });

        return rootView;
    }

    private OnFetchMoviesTaskCompleteListener fetchMoviesTaskCompleteListener = new OnFetchMoviesTaskCompleteListener() {
        @Override
        public void onTaskCompleted(ArrayList<Movie> movies) {
            MovieListAdapter movieListAdapter = (MovieListAdapter) gridView.getAdapter();
            movieListAdapter.setMovies(movies);
        }
    };

    public interface Callback {
        public void onItemSelected(String id);
    }
}