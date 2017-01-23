package com.elevation.hacks.modules;

import java.util.HashMap;
import java.util.List;

/**
 * Created by saurabh.malik1 on 1/23/2017.
 */

public interface ResultsListener {
    public void onResultsSucceeded(List<HashMap<String, String>> list);
}
