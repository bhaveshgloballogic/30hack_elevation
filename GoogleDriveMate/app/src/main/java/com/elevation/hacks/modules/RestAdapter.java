package com.elevation.hacks.modules;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elevation.hacks.R;

import java.util.ArrayList;


public class RestAdapter extends ArrayAdapter<RestPO> {
    public RestAdapter(Context context, ArrayList<RestPO> mRestpo) {
        super(context, 0, mRestpo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RestPO restPO = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rest_user, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);

       if(!TextUtils.isEmpty(restPO.getGoogleBreakDesc())){

           tvName.setText(restPO.getGoogleBreakDesc());
           tvName.setTextSize(24);
           tvHome.setVisibility(View.INVISIBLE);
           convertView.setBackgroundColor(Color.parseColor("#ff00ddff"));

       }else{
           if(!TextUtils.isEmpty(restPO.getPlace_name()) && !TextUtils.isEmpty(restPO.getVicinity())) {
               tvName.setText("  " + String.valueOf(position) + "." + " " + restPO.getPlace_name());
               tvHome.setText("      " + restPO.getVicinity());
               convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
           }
       }
        // Lookup view for data population

        // Populate the data into the template view using the data object

//        if (position % 2 == 0) {
//
//            convertView.setBackgroundColor(Color.parseColor("#ff00ddff"));
//
//        } else {
//
//
//
//        }
        // Return the completed view to render on screen
        return convertView;
    }

}
