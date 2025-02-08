package com.example.happy_tails;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WalkingActivityListAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<String> walkingActivitiesWalkingDatesList;
    ArrayList<String> walkingActivitiesDogsList;
    ArrayList<Long> walkingActivitiesTotalWalkingTimeList;
    ArrayList<Double> walkingActivitiesTotalWalkingDistanceList;
    ArrayList<String> walkingActivitiesDescriptionList;

    public WalkingActivityListAdapter(@NonNull Context context,
                                      ArrayList<String> walkingActivitiesWalkingDatesList,
                                      ArrayList<String> walkingActivitiesDogsList,
                                      ArrayList<Long> walkingActivitiesTotalWalkingTimeList,
                                      ArrayList<Double> walkingActivitiesTotalWalkingDistanceList,
                                      ArrayList<String> walkingActivitiesDescriptionList) {
        this.walkingActivitiesWalkingDatesList = walkingActivitiesWalkingDatesList;
        this.walkingActivitiesDogsList = walkingActivitiesDogsList;
        this.walkingActivitiesTotalWalkingTimeList = walkingActivitiesTotalWalkingTimeList;
        this.walkingActivitiesTotalWalkingDistanceList = walkingActivitiesTotalWalkingDistanceList;
        this.walkingActivitiesDescriptionList = walkingActivitiesDescriptionList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return walkingActivitiesWalkingDatesList.size();
    }

    @Override
    public Object getItem(int i) {
        return walkingActivitiesWalkingDatesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_walkingactivities, parent, false);
        }

        TextView layoutWalkingActivityDate = convertView.findViewById(R.id.LayoutWalkingActivityDate);
        TextView layoutWalkingActivityDog = convertView.findViewById(R.id.LayoutWalkingActivityDog);
        TextView layoutWalkingActivitiesTotalWalkingTime = convertView.findViewById(R.id.LayoutWalkingActivitiesTotalWalkingTime);
        TextView layoutWalkingActivitiesTotalDistance = convertView.findViewById(R.id.LayoutWalkingActivitiesTotalDistance);
        TextView layoutWalkingActivitiesWalkingDescription = convertView.findViewById(R.id.LayoutWalkingActivitiesWalkingDescription);

        // Assign values to views
        layoutWalkingActivityDate.setText(walkingActivitiesWalkingDatesList.get(i));
        layoutWalkingActivityDog.setText(walkingActivitiesDogsList.get(i));
        layoutWalkingActivitiesTotalWalkingTime.setText(String.valueOf(walkingActivitiesTotalWalkingTimeList.get(i)));
        layoutWalkingActivitiesTotalDistance.setText(String.valueOf(walkingActivitiesTotalWalkingDistanceList.get(i)));
        layoutWalkingActivitiesWalkingDescription.setText(walkingActivitiesDescriptionList.get(i));

        return convertView;
    }
}