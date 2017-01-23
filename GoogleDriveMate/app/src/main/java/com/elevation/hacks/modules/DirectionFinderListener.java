package com.elevation.hacks.modules;

import java.util.List;

/**
 * Created by saurabh.malik1 on 1/23/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
