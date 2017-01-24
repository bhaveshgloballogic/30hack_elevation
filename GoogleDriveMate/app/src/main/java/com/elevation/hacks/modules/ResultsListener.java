package com.elevation.hacks.modules;

import java.util.HashMap;
import java.util.List;

public interface ResultsListener {
    public void onResultsSucceeded(List<HashMap<String, String>> list);
    public void onPOIResultsSucceeded(List<HashMap<String, String>> list, int routeSeq);
}
