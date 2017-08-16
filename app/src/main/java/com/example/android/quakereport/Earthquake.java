package com.example.android.quakereport;

/**
 * Created by Desarrollo on 8/16/2017.
 */

public class Earthquake {
    private Double mMagnitude;
    private String mPlace;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquake(Double magnitude, String place, long time, String url){
        mMagnitude = magnitude;
        mPlace = place;
        mTimeInMilliseconds = time;
        mUrl = url;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getPlace() {
        return mPlace;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl(){
        return mUrl;
    }
}
