package com.elevation.hacks.modules;

import java.util.List;


public interface DirectionFinderListener {
    void onDirectionFinderStart();

    void onDirectionFinderSuccess(List<Route> route);
    void onDirectionFinderSuccessPOI(List<Route> route);
}
