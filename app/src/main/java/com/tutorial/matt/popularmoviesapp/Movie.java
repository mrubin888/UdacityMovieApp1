package com.tutorial.matt.popularmoviesapp;

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
}
