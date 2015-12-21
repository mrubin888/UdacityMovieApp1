package com.tutorial.matt.popularmoviesapp.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matt on 12/8/15.
 */
public class Movie implements Parcelable {

    private static final String TAG = Movie.class.getSimpleName();

    private String id;

    private String title;
    private String releaseDate;
    private String posterPath;
    private String voteAverage;
    private String overview;

    private boolean isFavorite = false;

    public Movie(JSONObject data) {
        try {
            id = data.getString("id");
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
        id = cursor.getString(cursor.getColumnIndex("_id"));

        title = cursor.getString(cursor.getColumnIndex("title"));
        releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
        posterPath = cursor.getString(cursor.getColumnIndex("poster_path"));
        voteAverage = cursor.getString(cursor.getColumnIndex("vote_average"));
        overview = cursor.getString(cursor.getColumnIndex("overview"));
        isFavorite = cursor.getInt(cursor.getColumnIndex("is_favorite")) != 0;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(voteAverage);
        dest.writeString(overview);
        dest.writeByte((byte) (isFavorite ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
