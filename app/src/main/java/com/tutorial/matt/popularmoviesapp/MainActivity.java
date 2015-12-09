package com.tutorial.matt.popularmoviesapp;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_API_ROOT = "http://api.themoviedb.org/3/discover/movie";
    public static final String API_KEY = “YOUR_API_KEY”;

    private Activity context;
    private Spinner spinner;
    private Button submitButton;
    private GridView gridView;

    private ArrayList<Movie> movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        String urlStr = buildUrlString("Most Popular");
        try {
            URL url = new URL(urlStr);
            new HTTPRequestTask(taskCompleteListener).execute(url);
        }
        catch (MalformedURLException e) {
            Log.e("URL", e.getMessage());
        }

        spinner = (Spinner) findViewById(R.id.sort_by_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movies.clear();

                String urlStr = buildUrlString((String) spinner.getSelectedItem());
                try {
                    URL url = new URL(urlStr);
                    new HTTPRequestTask(taskCompleteListener).execute(url);
                }
                catch (MalformedURLException e) {
                    Log.e("URL", e.getMessage());
                }
            }
        });
        gridView = (GridView) findViewById(R.id.poster_grid);
    }

    private OnAsyncTaskCompleteListener taskCompleteListener = new OnAsyncTaskCompleteListener() {
        @Override
        public void onTaskCompleted(String result) {
            try {
                JSONObject resultJson = new JSONObject(result);
                JSONArray resultsArr = (JSONArray) resultJson.get("results");
                for (int i = 0; i < resultsArr.length(); i++) {
                    JSONObject obj = (JSONObject) resultsArr.get(i);
                    Movie movie = new Movie(obj);

                    // Really we should load a static generic poster image in this case,
                    // but this is a bit of a lazy solution
                    if (!movie.getPosterPath().equals("null")) {
                        Log.d("Poster", movie.getPosterPath());
                        movies.add(movie);
                    }

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
            }
            catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        }
    };

    private String buildUrlString(String sortBy) {
        String urlStr = MOVIE_API_ROOT;
        urlStr += "?sort_by=";
        if ("Most Popular".equals(sortBy)) {
            urlStr += "popularity";
        }
        else if ("Highest Rated".equals(sortBy)){
            urlStr += "vote_average";
        }
        else {
            Log.e("URL", sortBy);
            return "";
        }
        urlStr += ".desc&api_key=" + API_KEY;
        Log.d("URL", urlStr);
        return urlStr;
    }
}
