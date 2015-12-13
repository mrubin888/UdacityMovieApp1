package com.tutorial.matt.popularmoviesapp;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matt on 12/8/15.
 */
public class Movie {

    private static final String TAG = Movie.class.getSimpleName();

    private String tmdbId;
    private String title;
    private String releaseDate;
    private String posterPath;
    private String voteAverage;
    private String overview;

    private boolean isFavorite = false;

    public Movie(JSONObject data) {
        try {
            tmdbId = data.getString("id");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            title = data.getString("title");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            releaseDate = data.getString("release_date");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            posterPath = data.getString("poster_path");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            voteAverage = data.getString("vote_average");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            overview = data.getString("overview");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Movie (Cursor cursor) {
        tmdbId = cursor.getString(cursor.getColumnIndex("tmdb_id"));
        title = cursor.getString(cursor.getColumnIndex("title"));
        releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
        posterPath = cursor.getString(cursor.getColumnIndex("poster_path"));
        voteAverage = cursor.getString(cursor.getColumnIndex("vote_average"));
        overview = cursor.getString(cursor.getColumnIndex("overview"));
        isFavorite = cursor.getInt(cursor.getColumnIndex("is_favorite")) != 0;
    }

    public String getTmdbId() { return tmdbId; }
    public void setTmdbId(String tmdbId) { this.tmdbId = tmdbId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getVoteAverage() { return voteAverage; }
    public void setVoteAverage(String voteAverage) { this.voteAverage = voteAverage; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public boolean isFavorite() { return isFavorite; }
}
