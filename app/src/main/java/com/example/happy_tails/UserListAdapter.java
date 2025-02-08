package com.example.happy_tails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<String> dogNamesList;
    private ArrayList<String> cityList;
    private ArrayList<String> dogBreedList;
    private ArrayList<String> dogDescriptionList;

    public UserListAdapter(@NonNull Context context, ArrayList<String> dogNamesList, ArrayList<String> cityList, ArrayList<String> dogBreedList, ArrayList<String> dogDescriptionList) {
        this.dogNamesList = dogNamesList;
        this.cityList = cityList;
        this.dogBreedList = dogBreedList;
        this.dogDescriptionList = dogDescriptionList;
        mInflater = LayoutInflater.from(context);  // Updated to use LayoutInflater.from()
    }

    @Override
    public int getCount() {
        return dogNamesList.size();
    }

    @Override
    public Object getItem(int i) {
        return dogNamesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            // Inflate only when view is null for better performance
            view = mInflater.inflate(R.layout.layout_happy_tails_friends, viewGroup, false);
        }

        TextView layoutFriendsDogName = view.findViewById(R.id.LayoutFriendsDogName);
        TextView layoutFriendsDogCity = view.findViewById(R.id.LayoutFriendsDogCity);
        TextView layoutFriendsDogBreed = view.findViewById(R.id.LayoutFriendsDogBreed);
        //TextView layoutFriendsDogDescription = view.findViewById(R.id.LayoutFriendsDogDescription);

        // Get the user information that we need for item layout
        String dogName = dogNamesList.get(i);
        String city = cityList.get(i);
        String dogBreed = dogBreedList.get(i);
        String description = dogDescriptionList.get(i);

        // Set the text for each TextView
        layoutFriendsDogName.setText(dogName);
        layoutFriendsDogCity.setText(city);
        layoutFriendsDogBreed.setText(dogBreed);
        //layoutFriendsDogDescription.setText(description);

        return view;
    }
}
