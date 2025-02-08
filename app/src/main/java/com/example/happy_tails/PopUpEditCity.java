package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpEditCity extends AppCompatActivity {
    private final AppCompatActivity activity = PopUpEditCity.this;

    private Spinner popUpEditCitySpinner;
    private Button popUpCitySaveButton;
    private Button popUpCityCancelButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_city);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initListener();
        initObjects();
    }

    private void initViews() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * 0.8);
        int height = (int) (dm.heightPixels * 0.4);
        getWindow().setLayout(width, height);

        popUpCitySaveButton = findViewById(R.id.PopUpCitySaveButton);
        popUpCityCancelButton = findViewById(R.id.PopUpCityCancelButton);
        popUpEditCitySpinner = findViewById(R.id.PopUpEditCitySpinner);

        // Populate spinner with cities
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(
                this, R.array.city_array, android.R.layout.simple_spinner_dropdown_item);
        popUpEditCitySpinner.setAdapter(staticAdapter);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
    }

    private void initListener() {
        popUpCitySaveButton.setOnClickListener(this::handleButtonClick);
        popUpCityCancelButton.setOnClickListener(this::handleButtonClick);
    }

    private void handleButtonClick(View v) {
        Intent previousEditProfileInformationIntent = getIntent();
        String userEmail = previousEditProfileInformationIntent.getStringExtra("ownersEmailForPassOn");

        if (v.getId() == R.id.PopUpCityCancelButton) {
            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(goBackIntent);
        }

        if (v.getId() == R.id.PopUpCitySaveButton) {
            String newCity = popUpEditCitySpinner.getSelectedItem().toString();
            if (!newCity.isEmpty()) {
                currentUser = databaseHelper.getSpecificUser(userEmail);
                currentUser.setCity(newCity);
                databaseHelper.updateUser(currentUser);
            }

            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(goBackIntent);
        }
    }
}
