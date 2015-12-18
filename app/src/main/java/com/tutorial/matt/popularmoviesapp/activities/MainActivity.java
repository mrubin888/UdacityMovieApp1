package com.tutorial.matt.popularmoviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tutorial.matt.popularmoviesapp.R;
import com.tutorial.matt.popularmoviesapp.fragments.MovieDetailsFragment;
import com.tutorial.matt.popularmoviesapp.fragments.MovieGridFragment;

public class MainActivity extends AppCompatActivity implements MovieGridFragment.Callback{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DETAIL_FRAG_TAG = "DFT";
    private boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_details_container) != null) {
            twoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details_container, new MovieDetailsFragment(), DETAIL_FRAG_TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (twoPane) {
            Bundle args = new Bundle();
            args.putString("id", id);

            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            movieDetailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, movieDetailsFragment, DETAIL_FRAG_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra("id", id);

            startActivity(intent);
        }
    }
}
