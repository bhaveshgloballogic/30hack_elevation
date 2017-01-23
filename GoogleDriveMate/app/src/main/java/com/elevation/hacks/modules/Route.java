package com.elevation.hacks.modules;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by saurabh.malik1 on 1/23/2017.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;
    public int routeColorCode;

    public List<LatLng> points;

    public List<LatLng> overviewPoints;

    //ToDo Need to put better object instead of just List
    public List<LatLng> breakPoints;
}
