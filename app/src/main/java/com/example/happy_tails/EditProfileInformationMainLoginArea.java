package com.example.happy_tails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;

public class EditProfileInformationMainLoginArea extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = EditProfileInformationMainLoginArea.this;

    private TextView profileInformationOwnerEmailTextView;
    private TextView profileInformationOwnerCityTextView;
    private TextView profileInformationOwnerDogNameTextView;
    private TextView profileInformationOwnerDogBreedTextView;
    private TextView profileInformationOwnerDogBirthdayTextView;
    private TextView profileInformationOwnerDogDescriptionTextView;

    private Button editProfileInformationUpdateInformationPasswordButton;
    private Button editProfileInformationUpdateInformationCityButton;
    private Button editProfileInformationUpdateInformationDogNameButton;
    private Button editProfileInformationUpdateInformationDogBreedButton;
    private Button editProfileInformationUpdateInformationDogBirthdayButton;
    //private Button editProfileInformationUpdateInformationDogDescriptionButton;
    private Button editProfileInformationBackToMainAreaButton;
    private Button editProfileInformationDeleteProfileButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_information_main_login_area);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initObjects();
        initViews();
        initListener();
    }

    /**
     * This method initializes views.
     */
    private void initViews() {
        editProfileInformationUpdateInformationPasswordButton = findViewById(R.id.EditProfileInformationUpdateInformationPasswordButton);
        editProfileInformationUpdateInformationCityButton = findViewById(R.id.EditProfileInformationUpdateInformationCityButton);
        editProfileInformationUpdateInformationDogNameButton = findViewById(R.id.EditProfileInformationUpdateInformationDogNameButton);
        editProfileInformationUpdateInformationDogBreedButton = findViewById(R.id.EditProfileInformationUpdateInformationDogBreedButton);
        editProfileInformationUpdateInformationDogBirthdayButton = findViewById(R.id.EditProfileInformationUpdateInformationDogBirthdayButton);
        //editProfileInformationUpdateInformationDogDescriptionButton = findViewById(R.id.EditProfileInformationUpdateInformationDogDescriptionButton);
        editProfileInformationBackToMainAreaButton = findViewById(R.id.EditProfileInformationBackToMainAreaButton);
        editProfileInformationDeleteProfileButton = findViewById(R.id.EditProfileInformationDeleteProfileButton);

        profileInformationOwnerEmailTextView = findViewById(R.id.ProfileInformationOwnerEmailTextView);
        profileInformationOwnerCityTextView = findViewById(R.id.ProfileInformationOwnerCityTextView);
        profileInformationOwnerDogNameTextView = findViewById(R.id.ProfileInformationOwnerDogNameTextView);
        profileInformationOwnerDogBreedTextView = findViewById(R.id.ProfileInformationOwnerDogBreedTextView);
        profileInformationOwnerDogBirthdayTextView = findViewById(R.id.ProfileInformationOwnerDogBirthdayTextView);
        //profileInformationOwnerDogDescriptionTextView = findViewById(R.id.ProfileInformationOwnerDogDescriptionTextView);

        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();

        if (b != null) {
            String userEmail = b.getString("ownersEmailForPassOn", "");
            profileInformationOwnerEmailTextView.setText(userEmail);

            currentUser = databaseHelper.getSpecificUser(userEmail);

            // Set user data
            profileInformationOwnerCityTextView.setText(currentUser.getCity());
            profileInformationOwnerDogNameTextView.setText(currentUser.getDogName());
            profileInformationOwnerDogBreedTextView.setText(currentUser.getDogBreed());
            profileInformationOwnerDogBirthdayTextView.setText(currentUser.getBirthday());
            //profileInformationOwnerDogDescriptionTextView.setText(currentUser.getDescription());
        }
    }

    /**
     * Initialize objects.
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * Initialize listeners.
     */
    private void initListener() {
        editProfileInformationUpdateInformationPasswordButton.setOnClickListener(this);
        editProfileInformationUpdateInformationCityButton.setOnClickListener(this);
        editProfileInformationUpdateInformationDogNameButton.setOnClickListener(this);
        editProfileInformationUpdateInformationDogBreedButton.setOnClickListener(this);
        editProfileInformationUpdateInformationDogBirthdayButton.setOnClickListener(this);
        //editProfileInformationUpdateInformationDogDescriptionButton.setOnClickListener(this);
        editProfileInformationBackToMainAreaButton.setOnClickListener(this);
        editProfileInformationDeleteProfileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent previousMainAreaIntent = getIntent();
        Bundle b = previousMainAreaIntent.getExtras();
        if (b == null) return;

        String userEmail = b.getString("ownersEmailForPassOn", "");
        Intent intent = null;

        if (v.getId() == R.id.EditProfileInformationUpdateInformationPasswordButton){
            intent = new Intent(activity, PopUpEditPassword.class);
        }
        else if (v.getId() == R.id.EditProfileInformationUpdateInformationCityButton) {
            intent = new Intent(activity, PopUpEditCity.class);
        }
        else if (v.getId() == R.id.EditProfileInformationUpdateInformationDogNameButton) {
            intent = new Intent(activity, PopUpEditDogName.class);
        }
        else if (v.getId() == R.id.EditProfileInformationUpdateInformationDogBreedButton) {
            intent = new Intent(activity, PopUpEditDogBreed.class);
        }
        else if (v.getId() == R.id.EditProfileInformationUpdateInformationDogBirthdayButton) {
            intent = new Intent(activity, PopUpEditBirthday.class);
        }
        else if (v.getId() == R.id.EditProfileInformationBackToMainAreaButton) {
            intent = new Intent(activity, LoginAreaMain.class);
        }
        else if (v.getId() == R.id.EditProfileInformationDeleteProfileButton){
            intent = new Intent(activity, PopUpDeleteAccount.class);
        }

        if (intent != null) {
            intent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(intent);
        }
    }

   @Override
    public void onBackPressed() {
        Intent previousMainAreaIntent = getIntent();
        Bundle b = previousMainAreaIntent.getExtras();

        if (b != null) {
            String userEmail = b.getString("ownersEmailForPassOn", "");
            Intent backToMainAreaActivityIntent = new Intent(activity, LoginAreaMain.class);
            backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(backToMainAreaActivityIntent);
        }
    }
}