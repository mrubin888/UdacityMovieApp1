package com.tutorial.matt.popularmoviesapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by matt on 12/10/15.
 */
public class MovieContentProvider extends ContentProvider {

    private static final String TAG = MovieContentProvider.class.getSimpleName();

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private MovieDbHelper movieDbHelper;

    private static final int MOVIES = 1;
    private static final int MOVIE_BY_ID = 2;

    private static final int REVIEWS = 101;

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, MOVIES);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE + "/*", MOVIE_BY_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case MOVIES: {
                Log.d(TAG, "MOVIES MATCHED");
                retCursor = movieDbHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }
            break;

            case MOVIE_BY_ID: {
                Log.d(TAG, "MOVIES BY ID");
                String id = uri.getPathSegments().get(1);
                Log.d(TAG, "FOR _id=" + id);

                retCursor = movieDbHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        null);
            }
            break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_BY_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = movieDbHelper.getWritableDatabase().insertWithOnConflict(MovieContract.MovieEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = null;
        if (uriMatcher.match(uri) == MOVIE_BY_ID) {
            id = uri.getPathSegments().get(1);
        }

        if (id != null) {
            return movieDbHelper.getWritableDatabase().delete(MovieContract.MovieEntry.TABLE_NAME, "_id=?", new String[]{id});
        }

        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = null;
        if (uriMatcher.match(uri) == MOVIE_BY_ID) {
            id = uri.getPathSegments().get(1);
        }

        if (id != null) {
            return movieDbHelper.getWritableDatabase().update(MovieContract.MovieEntry.TABLE_NAME, values, "_id=?", new String[]{id});
        }

        return -1;
    }
}
