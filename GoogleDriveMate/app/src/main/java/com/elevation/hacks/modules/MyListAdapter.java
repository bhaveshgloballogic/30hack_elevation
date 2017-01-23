package com.elevation.hacks.modules;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.elevation.hacks.R;

import java.util.ArrayList;

/**
 * Created by bhavesh.kumar on 1/24/2017.
 */

public class MyListAdapter  extends BaseExpandableListAdapter {
    ArrayList<RestPO> mRestlist;
    private Context context;

    public MyListAdapter(Context context, ArrayList<RestPO> mRestlist ) {
        this.context = context;
this.mRestlist = mRestlist;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }



    @Override
    public int getChildrenCount(int groupPosition) {


        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return 0;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {



        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        //RestPO detailInfo = (DetailInfo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.rest_user, null);
        }

        RestPO restPO = mRestlist.get(0);
        // Check if an existing view is being reused, otherwise inflate the view


        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvHome = (TextView) view.findViewById(R.id.tvHome);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        if(!TextUtils.isEmpty(restPO.getGoogleBreakDesc())){

            tvName.setText(restPO.getGoogleBreakDesc());
            tvName.setTextSize(24);
            tvHome.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
            view.setBackgroundColor(Color.parseColor("#ff00ddff"));

        }else{
            if(!TextUtils.isEmpty(restPO.getPlace_name()) && !TextUtils.isEmpty(restPO.getVicinity())) {
                tvName.setTextSize(14);
                tvHome.setVisibility(View.VISIBLE);
                tvHome.setVisibility(View.VISIBLE);
                tvName.setText("  " + String.valueOf(1) + "." + " " + restPO.getPlace_name());
                tvHome.setText("      " + restPO.getVicinity());
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            float r = restPO.getRating();
            ratingBar.setRating(r);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
