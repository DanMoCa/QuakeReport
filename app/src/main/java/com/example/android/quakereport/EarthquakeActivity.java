/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String USG_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private boolean mIsConnected;
    private EarthquakeAdapter mAdapter;
    private ListView mEarthquakeListView;
    private TextView mEmptyTextView;
    private ProgressBar mProgressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        mIsConnected = networkInfo != null && networkInfo.isConnected();
        mProgressSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        mEarthquakeListView = (ListView) findViewById(R.id.list);
        mEarthquakeListView.setEmptyView(mEmptyTextView);

        // Create a fake list of earthquake locations.
        LoaderManager loaderManager = getLoaderManager();
//        Log.v(LOG_TAG,"Loader Created");
        if(mIsConnected){
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
        }else{
            mProgressSpinner.setVisibility(View.GONE);
            mEmptyTextView.setText("No Internet Connection.");
        }
//        Log.v(LOG_TAG,"Loader initalized");

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this,new ArrayList<Earthquake>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        mEarthquakeListView.setAdapter(mAdapter);

        mEarthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake selectedEarthquake = (Earthquake) adapterView.getItemAtPosition(i);
                String url = selectedEarthquake.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG,"Loader Created");
        return new EarthquakeLoader(this,USG_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        mAdapter.clear();
        mProgressSpinner.setVisibility(View.GONE);

        Log.v(LOG_TAG,"Loader Finished");
        if(earthquakes != null && !earthquakes.isEmpty()){
            mAdapter.addAll(earthquakes);
            mEmptyTextView.setText("");
        }else{
            mEmptyTextView.setText("No Earthquakes Found.");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.v(LOG_TAG,"Loader Reset");
        mAdapter.clear();
    }

}
