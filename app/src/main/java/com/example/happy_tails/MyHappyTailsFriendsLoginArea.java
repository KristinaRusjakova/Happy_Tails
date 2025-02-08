package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MyHappyTailsFriendsLoginArea extends AppCompatActivity implements View.OnClickListener {

    private Button myPawllianceFriendsBackToMainAreaButton;
    private ListView listViewPawllianceFriends;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_happy_tails_friends_login_area);

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
        myPawllianceFriendsBackToMainAreaButton = findViewById(R.id.MyPawllianceFriendsBackToMainAreaButton);
        listViewPawllianceFriends = findViewById(R.id.ListViewPawllianceFriends);

        // Get user email from Intent extras
        String userEmail = getIntent().getStringExtra("ownersEmailForPassOn");
        populateListView();
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListener() {
        myPawllianceFriendsBackToMainAreaButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.MyPawllianceFriendsBackToMainAreaButton) {
            // Get user email from Intent extras
            String userEmail = getIntent().getStringExtra("ownersEmailForPassOn");

            // Navigate back to LoginAreaMain activity
            Intent backToMainAreaActivityIntent = new Intent(this, LoginAreaMain.class);
            backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(backToMainAreaActivityIntent);
        }
    }

    private void populateListView() {
        // Retrieve data from the database
        ArrayList<String> dogNamesList = databaseHelper.getAllUserDogNames();
        ArrayList<String> cityList = databaseHelper.getAllCities();
        ArrayList<String> dogBreedList = databaseHelper.getAllDogBreeds();
        ArrayList<String> dogDescriptionList = databaseHelper.getAllDogDescriptions();

        // Set up the adapter to display data in ListView
        UserListAdapter adapter = new UserListAdapter(this, dogNamesList, cityList, dogBreedList, dogDescriptionList);
        listViewPawllianceFriends.setAdapter(adapter);
    }
}
