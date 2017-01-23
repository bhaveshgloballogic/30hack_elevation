package com.elevation.hacks.modules;


import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String> {
    String googlePlacesData = null;
    GoogleMap googleMap;
    ResultsListener listener;
    String breakDes;
    Boolean isPOI;

    public void setOnResultsListener(ResultsListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Object... inputObj) {
        try {
            googleMap = (GoogleMap) inputObj[0];
            String googlePlacesUrl = (String) inputObj[1];
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
            setBreakDesc((int) inputObj[2]);
            isPOI = (boolean)inputObj[3];
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask();
        Object[] toPass = new Object[4];
        toPass[0] = googleMap;
        toPass[1] = result;
        toPass[2] = breakDes;
        toPass[3] = isPOI;
        placesDisplayTask.setOnResultsListener(listener);
        placesDisplayTask.execute(toPass);
    }

    private void setBreakDesc(int breakSeq){
        switch (breakSeq) {
            case 0:
                breakDes = "Break";
                break;
            case 1:
                breakDes = "Second Break";
                break;
            case 2:
                breakDes = "Third Break";
                break;
            case 3:
                breakDes = "Fourth Break";
                break;
            case 4:
                breakDes = "Fifth Break";
                break;
            case 5:
                breakDes = "Sixth Break";
                break;
            case 6:
                breakDes = "Seventh Break";
                break;
        }
    }
}