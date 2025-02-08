package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpEditDogBreed extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = PopUpEditDogBreed.this;

    private Spinner popUpEditDogBreedSpinner;
    private Button popUpDogBreedSaveButton, popUpDogBreedCancelButton;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_dog_breed);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initObjects();
        initListener();
    }

    /**
     * This method initializes views.
     */
    private void initViews() {
        // Get Display Metrics
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Set Pop-Up Window Size
        int width = (int) (dm.widthPixels * 0.8);
        int height = (int) (dm.heightPixels * 0.4);
        getWindow().setLayout(width, height);

        popUpDogBreedSaveButton = findViewById(R.id.PopUpDogBreedSaveButton);
        popUpDogBreedCancelButton = findViewById(R.id.PopUpDogBreedCancelButton);
        popUpEditDogBreedSpinner = findViewById(R.id.PopUpEditDogBreedSpinner);

        // Set Up Spinner Adapter
        ArrayAdapter<CharSequence> staticBreedAdapter = ArrayAdapter.createFromResource(
                this, R.array.dogBreeds_array, android.R.layout.simple_spinner_item
        );
        staticBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        popUpEditDogBreedSpinner.setAdapter(staticBreedAdapter);
    }

    /**
     * This method initializes objects.
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
    }

    /**
     * This method initializes listeners.
     */
    private void initListener() {
        popUpDogBreedSaveButton.setOnClickListener(this);
        popUpDogBreedCancelButton.setOnClickListener(this);
    }

    /**
     * Handles button clicks using if-else instead of switch-case.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.PopUpDogBreedCancelButton) {
            goBackToEditProfile();
        } else if (id == R.id.PopUpDogBreedSaveButton) {
            saveDogBreed();
        }
    }

    /**
     * Navigates back to the Edit Profile page.
     */
    private void goBackToEditProfile() {
        Intent previousIntent = getIntent();
        Bundle b = previousIntent.getExtras();
        if (b != null) {
            String userEmail = b.getString("ownersEmailForPassOn");
            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            finish();
            startActivity(goBackIntent);
        }
    }

    /**
     * Saves the selected dog breed to the database.
     */
    private void saveDogBreed() {
        Intent previousIntent = getIntent();
        Bundle b = previousIntent.getExtras();
        if (b != null) {
            String userEmail = b.getString("ownersEmailForPassOn");
            System.out.println("This is the owner's email: " + userEmail);

            // Get selected dog breed
            String newDogBreed = popUpEditDogBreedSpinner.getSelectedItem().toString();
            if (!newDogBreed.isEmpty()) {
                currentUser = databaseHelper.getSpecificUser(userEmail);
                currentUser.setDogBreed(newDogBreed);
                databaseHelper.updateUser(currentUser);
            }

            // Navigate back
            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            finish();
            startActivity(goBackIntent);
        }
    }
}
