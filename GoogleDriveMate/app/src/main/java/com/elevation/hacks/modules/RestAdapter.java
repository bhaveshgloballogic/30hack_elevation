package com.elevation.hacks.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.elevation.hacks.R;

import java.util.ArrayList;



public class RestAdapter extends ArrayAdapter<RestPO> {
    public RestAdapter(Context context, ArrayList<RestPO> mRestpo) {
        super(context,0, mRestpo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RestPO restPO = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rest_user, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        tvName.setText(restPO.getPlace_name());
        tvHome.setText(restPO.getVicinity());
        // Return the completed view to render on screen
        return convertView;
    }

}
