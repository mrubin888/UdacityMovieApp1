package com.tutorial.matt.popularmoviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.models.Review;

import java.util.ArrayList;

/**
 * Created by matt on 12/8/15.
 */
public class ReviewListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Review> reviews;

    public ReviewListAdapter(Context context) {
        this(context, new ArrayList<Review>());
    }

    public ReviewListAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ReviewHolder {
        TextView authorView;
        TextView contentView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewHolder holder = new ReviewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = inflater.inflate(R.layout.list_item_review, null);
        Review review = reviews.get(position);
        holder.authorView = (TextView) cellView.findViewById(R.id.review_author_text);
        holder.authorView.setText(review.getAuthor());
        holder.contentView = (TextView) cellView.findViewById(R.id.review_content_text);
        holder.contentView.setText(review.getContent());
        return cellView;
    }

    public void setReviews (ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
