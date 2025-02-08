package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpDeleteAccount extends AppCompatActivity {
    private final AppCompatActivity activity = PopUpDeleteAccount.this;

    private Button popUpDeleteAccountButton;
    private Button popUpDontDeleteAccountButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_delete_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initObjects();
        initListeners();
    }

    /**
     * Initializes UI components.
     */
    private void initViews() {
        adjustPopUpWindowSize();

        popUpDeleteAccountButton = findViewById(R.id.PopUpDeleteAccountButton);
        popUpDontDeleteAccountButton = findViewById(R.id.PopUpDontDeleteAccountButton);
    }

    /**
     * Adjusts the pop-up window size dynamically.
     */
    private void adjustPopUpWindowSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * 0.9);
        int height = (int) (dm.heightPixels * 0.6);
        getWindow().setLayout(width, height);
    }

    /**
     * Initializes database-related objects.
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
    }

    /**
     * Sets up click listeners for buttons.
     */
    private void initListeners() {
        popUpDeleteAccountButton.setOnClickListener(this::handleClick);
        popUpDontDeleteAccountButton.setOnClickListener(this::handleClick);
    }

    /**
     * Handles button click events.
     */
    private void handleClick(View v) {
        String userEmail = getUserEmailFromIntent();
        if (userEmail == null) return;

        if (v.getId() == R.id.PopUpDontDeleteAccountButton) {
            navigateTo(EditProfileInformationMainLoginArea.class, userEmail);
        } else if (v.getId() == R.id.PopUpDeleteAccountButton) {
            deleteUserAccount(userEmail);
            navigateTo(MainActivity.class, null);
        }
    }

    /**
     * Retrieves the user's email from the intent.
     */
    private String getUserEmailFromIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        return extras != null ? extras.getString("ownersEmailForPassOn") : null;
    }

    /**
     * Deletes the user account and related data.
     */
    private void deleteUserAccount(String userEmail) {
        currentUser = databaseHelper.getSpecificUser(userEmail);
        if (currentUser != null) {
            int userID = currentUser.getUserID();
            databaseHelper.deleteAllUserWalkingActivity(userID);
            databaseHelper.deleteUser(currentUser);
        }
    }

    /**
     * Navigates to another activity.
     */
    private void navigateTo(Class<?> targetActivity, String userEmail) {
        Intent intent = new Intent(activity, targetActivity);
        if (userEmail != null) {
            intent.putExtra("ownersEmailForPassOn", userEmail);
        }
        startActivity(intent);
    }
}
