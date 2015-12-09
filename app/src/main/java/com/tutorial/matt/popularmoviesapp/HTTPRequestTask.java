package com.tutorial.matt.popularmoviesapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by matt on 12/8/15.
 */
public class HTTPRequestTask extends AsyncTask<URL, Void, String> {
    private OnAsyncTaskCompleteListener listener;

    public HTTPRequestTask (OnAsyncTaskCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(URL... params) {
        String result = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) params[0].openConnection();

            try {
                InputStream in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder readerResult = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    readerResult.append(line);
                }
                result = readerResult.toString();
            }
            finally {
                connection.disconnect();
            }
        }
        catch (IOException e) {
            Log.e("HTTP", e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        this.listener.onTaskCompleted(result);
    }
}