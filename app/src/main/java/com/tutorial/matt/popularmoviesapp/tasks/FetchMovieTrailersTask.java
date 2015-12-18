package com.tutorial.matt.popularmoviesapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.listeners.OnFetchMovieTrailersTaskCompleteListener;
import com.tutorial.matt.popularmoviesapp.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by matt on 12/13/15.
 */
public class FetchMovieTrailersTask extends AsyncTask<String, Void, ArrayList<Trailer>> {

    private static final String TAG = FetchMovieTrailersTask.class.getSimpleName();

    private Context context;
    private OnFetchMovieTrailersTaskCompleteListener listener;

    public FetchMovieTrailersTask (Context context, OnFetchMovieTrailersTaskCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Trailer> doInBackground(String... params) {

        String movieId = params[0];

        String resultStr = "";
        ArrayList<Trailer> trailers = new ArrayList<Trailer>();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String urlStr = buildUrlString(movieId);
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
                Trailer trailer = new Trailer(obj);
                trailers.add(trailer);
            }
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        Log.d (TAG, "Task should post execute");
        Log.d (TAG, "Found " + trailers.size() + " reviews.");
        return trailers;
    }

    @Override
    protected void onPostExecute(ArrayList<Trailer> trailers) {
        Log.d (TAG, "Task onPostExecute");
        this.listener.onTaskCompleted(trailers);
    }

    private String buildUrlString(String movieId) {
        String urlStr = context.getResources().getString(R.string.movie_detail_api_root) + "/" + movieId + "/videos";
        urlStr += "?api_key=" + context.getResources().getString(R.string.movie_api_key);

        return urlStr;
    }
}
