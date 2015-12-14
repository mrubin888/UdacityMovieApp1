package com.tutorial.matt.popularmoviesapp.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matt on 12/8/15.
 */
public class Review {

    private static final String TAG = Review.class.getSimpleName();

    private String id;

    private String author;
    private String content;

    public Review(JSONObject data) {
        try {
            id = data.getString("id");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            author = data.getString("author");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            content = data.getString("content");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
