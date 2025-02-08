package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class PopUpDeleteWalkingActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = PopUpDeleteWalkingActivity.this;

    private Button popUpDeleteWalkingActivityButton;
    private Button popUpDontDeleteWalkingActivityButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;
    private WalkingActivity currentWalkingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_delete_walking);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initObjects();
        initListener();
    }

    /**
     * This method initializes views to be used.
     */
    private void initViews() {
        // Getting Display Metrics of screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Setting Pop-Up Window size
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.6));

        popUpDeleteWalkingActivityButton = findViewById(R.id.PopUpDeleteWalkingActivityButton);
        popUpDontDeleteWalkingActivityButton = findViewById(R.id.PopUpDontDeleteWalkingActivityButton);
    }

    /**
     * This method initializes objects to be used.
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
        currentWalkingActivity = new WalkingActivity();
    }

    /**
     * This method initializes listeners.
     */
    private void initListener() {
        popUpDeleteWalkingActivityButton.setOnClickListener(this);
        popUpDontDeleteWalkingActivityButton.setOnClickListener(this);
    }

    /**
     * This method handles click events.
     */
    @Override
    public void onClick(View v) {
        Intent previousThanksForYourDogWalkActivity = getIntent();
        String userEmail = previousThanksForYourDogWalkActivity.getStringExtra("ownersEmailForPassOn");
        int passOnWalkingID = previousThanksForYourDogWalkActivity.getIntExtra("walkingIDForPassOn", -1);

        if (v.getId() == R.id.PopUpDeleteWalkingActivityButton) {
                currentWalkingActivity = databaseHelper.getSpecificWalkingActivity(passOnWalkingID);
                databaseHelper.deleteWalkingActivity(currentWalkingActivity);

                Intent goBackToLoginMainAreaIntent = new Intent(activity, LoginAreaMain.class);
                goBackToLoginMainAreaIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToLoginMainAreaIntent);
        } else if (v.getId() == R.id.PopUpDontDeleteWalkingActivityButton) {
            Intent goBack = new Intent(activity, MyWalkingActivitiesLoginArea.class);
            goBack.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(goBack);
        }
    }
}