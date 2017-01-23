package modules;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import modules.Route;

/**
 * Created by saurabh.malik1 on 1/23/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
