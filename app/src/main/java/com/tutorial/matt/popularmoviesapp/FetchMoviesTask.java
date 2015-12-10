package com.tutorial.matt.popularmoviesapp;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.tutorial.matt.popularmoviesapp.data.MovieContract.MovieEntry;
import com.tutorial.matt.popularmoviesapp.data.MovieDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by matt on 12/8/15.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private static final String TAG = FetchMoviesTask.class.getSimpleName();

    private Context context;
    private OnFetchMoviesTaskCompleteListener listener;

    public FetchMoviesTask (Context context, OnFetchMoviesTaskCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    // params[0] = sort_by from spinner
    protected ArrayList<Movie> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String resultStr = "";
        ArrayList<Movie> movies = new ArrayList<Movie>();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String urlStr = buildUrlString(params[0]);
        URL url = null;
        try {
            url = new URL(urlStr);
        }
        catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                return null;
            }

            resultStr = buffer.toString();
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        try {
            JSONObject resultJson = new JSONObject(resultStr);
            JSONArray resultsArr = (JSONArray) resultJson.get("results");
            for (int i = 0; i < resultsArr.length(); i++) {
                JSONObject obj = (JSONObject) resultsArr.get(i);
                Movie movie = new Movie(obj);

                // Really we should load a static generic poster image in this case,
                // but this is a bit of a lazy solution
                if (!movie.getPosterPath().equals("null")) {
                    movies.add(movie);
                }
            }
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        ArrayList<ContentValues> cvList = new ArrayList<ContentValues>();
        for (Movie movie : movies) {
            ContentValues cv = new ContentValues();
            cv.put(MovieEntry.COLUMN_TMDB_ID, movie.getTmdbId());
            cv.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
            cv.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            cv.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            cv.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());

            cvList.add(cv);
        }

        if (!cvList.isEmpty()) {
            MovieDbHelper dbHelper = new MovieDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            for (ContentValues cv : cvList) {
                db.insert(MovieEntry.TABLE_NAME, null, cv);
            }
            db.endTransaction();
        }

        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        this.listener.onTaskCompleted(movies);
    }

    private String buildUrlString(String sortBy) {
        String urlStr = context.getResources().getString(R.string.movie_api_root);
        urlStr += "?sort_by=";

        String[] sortByOptions = context.getResources().getStringArray(R.array.sort_by_array);
        if (sortByOptions[0].equals(sortBy)) {
            urlStr += "popularity";
        }
        else if (sortByOptions[1].equals(sortBy)){
            urlStr += "vote_average";
        }
        else {
            Log.e(TAG, sortBy);
            return "";
        }
        urlStr += ".desc&api_key=" + context.getResources().getString(R.string.movie_api_key);
        Log.d(TAG, urlStr);
        return urlStr;
    }
}