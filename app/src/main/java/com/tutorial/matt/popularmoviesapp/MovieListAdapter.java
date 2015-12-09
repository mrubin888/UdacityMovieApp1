package com.tutorial.matt.popularmoviesapp;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by matt on 12/8/15.
 */
public class MovieListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;

    public MovieListAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class MovieHolder {
        ImageView posterImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieHolder holder = new MovieHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = inflater.inflate(R.layout.list_item_poster_button, null);
        Movie movie = movies.get(position);
        holder.posterImage = (ImageView) cellView.findViewById(R.id.poster_image);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).into(holder.posterImage);
        return cellView;
    }
}
