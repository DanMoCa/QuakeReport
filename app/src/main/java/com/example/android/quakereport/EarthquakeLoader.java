package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Desarrollo on 8/18/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;

    public EarthquakeLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG,"Start Start Loading");
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.v(LOG_TAG,"Loader Load in Background");
        if(mUrl == null){
            return null;
        }

        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakesData(mUrl);
        return earthquakes;
    }
}
