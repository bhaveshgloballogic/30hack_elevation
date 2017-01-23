package com.elevation.hacks.modules;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class DirectionFinder {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyAhoQEcIJCI_mpRInfAJmyN3RPp_QmXRd4";
    private DirectionFinderListener listener;
    private String origin;
    private String destination;

    public DirectionFinder(DirectionFinderListener listener, String origin, String destination) {
        this.listener = listener;
        this.origin = origin;
        this.destination = destination;
    }

    public void execute() throws UnsupportedEncodingException {
        listener.onDirectionFinderStart();
        new DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");

        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&key=" + GOOGLE_API_KEY;
    }

    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;

        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.endAddress = jsonLeg.getString("end_address");
            route.startAddress = jsonLeg.getString("start_address");
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.overviewPoints = decodePolyLine(overview_polylineJson.getString("points"));
            //Set detailed journey points
            route.points = new ArrayList<LatLng>();
            addJourneyPoints(route, jsonLeg);

            routes.add(route);
        }

        listener.onDirectionFinderSuccess(routes);
    }

    private void addJourneyPoints(Route route, JSONObject jsonLeg) throws JSONException {
        int totalDistance;
        int totalDuration;
        int distanceCounter = 0;
        int legDistance = 0;
        totalDistance = jsonLeg.getJSONObject("distance").getInt("value");
        totalDuration = jsonLeg.getJSONObject("duration").getInt("value");
        List<JSONObject> breakLegs = new ArrayList<>();
        route.breakPoints = new ArrayList<>();

        List<Distance> distanceBreaks = getBreakDistance(totalDistance);

        JSONArray jsonSteps = jsonLeg.getJSONArray("steps");

        for (int i = 0; i < jsonSteps.length(); i++) {
            boolean isBreakLeg = false;
            JSONObject step = jsonSteps.getJSONObject(i);
            legDistance = step.getJSONObject("distance").getInt("value");
            double legBrPointRatio = 0.0;
            //Add Legs on which break required.
            for(Distance db: distanceBreaks){
                if(distanceCounter < db.value && db.value <= (distanceCounter + legDistance)){
                    step.put("startMilestone", distanceCounter);
                    step.put("endMilestone", distanceCounter + legDistance);
                    breakLegs.add(step);
                    isBreakLeg = true;
                    legBrPointRatio = (db.value - distanceCounter)/legDistance;
                    break;
                }
            }
            distanceCounter = distanceCounter + legDistance;
            //Get Points decoded from leg's polyline
            List<LatLng> points = decodePolyLine(step.getJSONObject("polyline").getString("points"));
            if(isBreakLeg){
                //ToDo Call some logic to get aprox coordinates for break point
                //Right now Getting based on leg's break point Ratio
                route.breakPoints.add(points.get((int)(points.size() * legBrPointRatio)));
            }
            route.points.addAll(points);
        }
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }

    private List<Distance> getBreakDistance(int totalDistance){
        List<Distance> distanceBreakPoints = new ArrayList<>();

        //Convert from Mtrs to KMs
        totalDistance = totalDistance;

        if(totalDistance <= 400*1000){
            distanceBreakPoints.add(new Distance("Mid Break", totalDistance/2));
        }
        else if(totalDistance <= 900*1000){
            //First Break
            distanceBreakPoints.add(new Distance("First Break", totalDistance/3));
            //Second Break
            distanceBreakPoints.add(new Distance("Second Break", 2*totalDistance/3));
        }
        else{
            int breakDistance = 0;
            while (totalDistance > 300*1000){
                breakDistance = breakDistance + 300*1000;

                distanceBreakPoints.add(new Distance("Rest Break", breakDistance));

                totalDistance = totalDistance - 300*1000;
            }
        }
        return distanceBreakPoints;
    }

    private List<Duration> getBreakDuration(double totalDuration){
        List<Duration> durationBreakPoints = new ArrayList<>();

        return durationBreakPoints;
    }

    private List<LatLng> getStops(JSONArray jsonLegs) throws JSONException {
        List<LatLng> stops = new ArrayList<LatLng>();
        for (int i = 0; i < jsonLegs.length(); i++) {
            JSONObject leg = jsonLegs.getJSONObject(i);

        }

        return stops;
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
