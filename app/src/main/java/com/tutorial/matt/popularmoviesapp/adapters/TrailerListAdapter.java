package com.tutorial.matt.popularmoviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.models.Trailer;

import java.util.ArrayList;

/**
 * Created by matt on 12/8/15.
 */
public class TrailerListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Trailer> trailers;

    public TrailerListAdapter(Context context) {
        this(context, new ArrayList<Trailer>());
    }

    public TrailerListAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class TrailerHolder {
        TextView nameView;
        TextView linkView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrailerHolder holder = new TrailerHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = inflater.inflate(R.layout.list_item_trailer, null);
        Trailer trailer = trailers.get(position);
        holder.nameView = (TextView) cellView.findViewById(R.id.trailer_name_text);
        holder.nameView.setText(trailer.getName());
        holder.linkView = (TextView) cellView.findViewById(R.id.trailer_link);
        holder.linkView.setText(trailer.getUrl());
        return cellView;
    }

    public void setTrailers (ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }
}
