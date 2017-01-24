package com.elevation.hacks.modules;

/**
 * Created by saurabh.malik1 on 1/24/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class GooglePOIReadTask extends AsyncTask<Object, Integer, String> {
    String googlePlacesData = null;
    ResultsListener listener;
    String breakDes;
    Boolean isPOI;

    public void setOnResultsListener(ResultsListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Object... inputObj) {
        try {
            String googlePlacesUrl = (String) inputObj[0];
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        POIDisplayTask poiDisplayTask = new POIDisplayTask();
        Object[] toPass = new Object[1];
        toPass[0] = result;
        poiDisplayTask.setOnResultsListener(listener);
        poiDisplayTask.execute(toPass);
    }
}