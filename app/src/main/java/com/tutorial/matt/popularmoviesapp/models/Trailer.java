package com.tutorial.matt.popularmoviesapp.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matt on 12/14/15.
 */
public class Trailer {

    private static final String TAG = Trailer.class.getSimpleName();

    private String id;

    private String name;
    private String site;
    private String key;

    public Trailer(JSONObject data) {
        try {
            id = data.getString("id");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            name = data.getString("name");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            site = data.getString("site");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            key = data.getString("key");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setAuthor(String name) { this.name = name; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getUrl() {
        if ("YouTube".equals(site)) {
            return "https://www.youtube.com/watch?v=" + key;
        }
        return null;
    }
}
