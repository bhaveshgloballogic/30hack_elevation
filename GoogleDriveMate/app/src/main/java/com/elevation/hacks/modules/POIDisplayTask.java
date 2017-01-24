package com.elevation.hacks.modules;

/**
 * Created by saurabh.malik1 on 1/24/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class POIDisplayTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

    JSONObject googlePlacesJson;
    GoogleMap googleMap;
    ResultsListener listener;
    Boolean isPOI;


    public void setOnResultsListener(ResultsListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

        List<HashMap<String, String>> googlePlacesList = null;
        Places placeJsonParser = new Places();

        try {
            googlePlacesJson = new JSONObject((String) inputObj[0]);
            googlePlacesList = placeJsonParser.parse(googlePlacesJson);
            HashMap<String, String> googleBreakDesc = new HashMap<String, String>();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {

        listener.onPOIResultsSucceeded(list);
    }
}

