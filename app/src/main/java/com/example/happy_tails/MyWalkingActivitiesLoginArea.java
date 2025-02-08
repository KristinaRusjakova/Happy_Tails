package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyWalkingActivitiesLoginArea extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = MyWalkingActivitiesLoginArea.this;

    private Button myWalkingActivitiesBackToMainAreaButton;
    private ListView listViewMyWalkingActivities;

    private DatabaseHelper databaseHelper;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_walking_activities_login_area);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initObjects();
        initViews();
        initListener();
    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews() {
        myWalkingActivitiesBackToMainAreaButton = findViewById(R.id.MyWalkingActivitiesBackToMainAreaButton);
        listViewMyWalkingActivities = findViewById(R.id.ListViewMyWalkingActivities);

        // Get user email from the intent passed by the previous activity
        Intent previousLoginAreaIntent = getIntent();
        String userEmail = previousLoginAreaIntent.getStringExtra("ownersEmailForPassOn");
        populateListView(userEmail);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListener() {
        myWalkingActivitiesBackToMainAreaButton.setOnClickListener(this);
        // myWalkingActivityDeleteWalkingActivityButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.MyWalkingActivitiesBackToMainAreaButton) {
                // Get user email and pass it to the next activity
                Intent previousMainAreaIntent = getIntent();
                String userEmail = previousMainAreaIntent.getStringExtra("ownersEmailForPassOn");

                // Intent to go back to the main area (LoginAreaMain)
                Intent backToMainAreaActivityIntent = new Intent(activity, LoginAreaMain.class);
                backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(backToMainAreaActivityIntent);
                setResult(RESULT_OK, backToMainAreaActivityIntent);
                finish();
        }
    }

    private void populateListView(String userEmail) {

        // Get the current user from the database based on the provided email
        currentUser = databaseHelper.getSpecificUser(userEmail);
        System.out.println("This is the current user Email " + currentUser.getEmail());
        int currentUserID = currentUser.getUserID();
        System.out.println("This is the current userID: " + currentUserID);

        ArrayList<String> walkingActivitiesWalkingDatesList = databaseHelper.getAllWalkingActivityWalkingDates(currentUserID);
        ArrayList<String> walkingActivitiesDogsList = databaseHelper.getAllWalkingActivityDogs(currentUserID);
        ArrayList<Long> walkingActivitiesTotalWalkingTimeList = databaseHelper.getAllWalkingActivityTotalWalkingTime(currentUserID);
        ArrayList<Double> walkingActivitiesTotalWalkingDistanceList = databaseHelper.getAllWalkingActivityTotalWalkingDistance(currentUserID);
        ArrayList<String> walkingActivitiesDescriptionList = databaseHelper.getAllWalkingActivityDescriptions(currentUserID);

        WalkingActivityListAdapter adapter = new WalkingActivityListAdapter(this, walkingActivitiesWalkingDatesList, walkingActivitiesDogsList, walkingActivitiesTotalWalkingTimeList, walkingActivitiesTotalWalkingDistanceList, walkingActivitiesDescriptionList);
        listViewMyWalkingActivities.setAdapter(adapter);

        listViewMyWalkingActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create an Intent for deleting a walking activity
                Intent deleteWalkIntent = new Intent(getApplicationContext(), PopUpDeleteWalkingActivity.class);
                // Pass necessary data to the next activity (email and walking ID)
                deleteWalkIntent.putExtra("ownersEmailForPassOn", userEmail);
                deleteWalkIntent.putExtra("walkingIDForPassOn", position); // position in the list
                startActivity(deleteWalkIntent);
            }
        });
    }
}