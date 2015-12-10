package com.tutorial.matt.popularmoviesapp.data;

import com.tutorial.matt.popularmoviesapp.data.MovieContract.MovieEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matt on 12/10/15.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MovieEntry.COLUMN_TMDB_ID + " STRING UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " STRING NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " STRING NOT NULL, " +
                MovieEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " STRING NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW + " STRING NOT NULL, " +

                MovieEntry.COLUMN_DATE_CREATED + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}