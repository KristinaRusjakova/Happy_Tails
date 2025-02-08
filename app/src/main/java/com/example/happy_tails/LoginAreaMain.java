package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginAreaMain extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginAreaMain.this;
    private TextView loginAreaEmailTextView;
    private Button loginAreaLetsGoForAWalkButton;
    private Button loginAreaMyWalkingActivitiesButton;
    private Button loginAreaFindNewFurryFriendsButton;
    private Button loginAreaLogoutButton;
    private Button loginAreaUpdateProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_area_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initListener();
    }
    private void initViews() {

        loginAreaLetsGoForAWalkButton = findViewById(R.id.LoginAreaLetsGoForAWalkButton);
        loginAreaMyWalkingActivitiesButton = findViewById(R.id.LoginAreaMyWalkingActivitiesButton);
        loginAreaFindNewFurryFriendsButton = findViewById(R.id.LoginAreaFindNewFurryFriendsButton);
        loginAreaUpdateProfileButton = findViewById(R.id.LoginAreaUpdateProfileButton);
        loginAreaLogoutButton = findViewById(R.id.LoginAreaLogoutButton);

        String userEmail = "You're logged in!";
        loginAreaEmailTextView = findViewById(R.id.LoginAreaEmailTextView);
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        if (b != null) {
            userEmail = b.getString("ownersEmailForPassOn", userEmail);
        }
        loginAreaEmailTextView.setText(userEmail);
    }

    private void initListener() {
        loginAreaLetsGoForAWalkButton.setOnClickListener(this);
        loginAreaMyWalkingActivitiesButton.setOnClickListener(this);
        loginAreaFindNewFurryFriendsButton.setOnClickListener(this);
        loginAreaUpdateProfileButton.setOnClickListener(this);
        loginAreaLogoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String userEmail = "defaultEmail@example.com";  // Default email if no extras are passed
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        if (b != null) {
            userEmail = b.getString("ownersEmailForPassOn", userEmail);
        }

        if (v.getId() == R.id.LoginAreaLetsGoForAWalkButton) {
            Intent mapsActivityIntent = new Intent(activity, MapsActivity.class);
            mapsActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(mapsActivityIntent);

        } else if (v.getId() == R.id.LoginAreaMyWalkingActivitiesButton) {
            Intent myHappyTailsActivities = new Intent(activity, MyWalkingActivitiesLoginArea.class);
            myHappyTailsActivities.putExtra("ownersEmailForPassOn", userEmail);
            startActivityForResult(myHappyTailsActivities, 1);

        } else if (v.getId() == R.id.LoginAreaLogoutButton) {
            Intent logoutIntent = new Intent(activity, MainActivity.class);
            startActivity(logoutIntent);
        } else if (v.getId() == R.id.LoginAreaFindNewFurryFriendsButton) {
            Intent findNewFriendsActivityIntent = new Intent(activity, MyHappyTailsFriendsLoginArea.class);
            findNewFriendsActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(findNewFriendsActivityIntent);
        } else if (v.getId() == R.id.LoginAreaUpdateProfileButton) {
            Intent updateProfileActivityIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            updateProfileActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(updateProfileActivityIntent);
        } else if (v.getId() == R.id.LoginAreaLogoutButton) {
            Intent logoutIntent = new Intent(activity, MainActivity.class);
            startActivity(logoutIntent);
        }
    }
}