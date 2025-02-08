package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PopUpEditDogName extends AppCompatActivity {
    private final AppCompatActivity activity = PopUpEditDogName.this;

    private Button popUpDogNameCancelButton;
    private Button popUpDogNameSaveButton;

    private TextInputLayout popUpDogNameTextInputLayout;
    private TextInputEditText popUpDogNameTextInputEditText;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_dog_name);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initObjects();
        initListener();
    }

    private void initViews() {
        // Set the pop-up window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.4));

        popUpDogNameTextInputLayout = findViewById(R.id.PopUpDogNameTextInputLayout);
        popUpDogNameTextInputEditText = findViewById(R.id.PopUpDogNameTextInputEditText);

        popUpDogNameCancelButton = findViewById(R.id.PopUpDogNameCancelButton);
        popUpDogNameSaveButton = findViewById(R.id.PopUpDogNameSaveButton);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
    }

    private void initListener() {
        popUpDogNameCancelButton.setOnClickListener(this::handleButtonClick);
        popUpDogNameSaveButton.setOnClickListener(this::handleButtonClick);
    }

    private void handleButtonClick(View v) {
        Intent previousEditProfileInformationIntent = getIntent();
        String userEmail = previousEditProfileInformationIntent.getStringExtra("ownersEmailForPassOn");

        if (v.getId() == R.id.PopUpDogNameCancelButton) {
            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(goBackIntent);
        }

        if (v.getId() == R.id.PopUpDogNameSaveButton) {
            String newDogName = popUpDogNameTextInputEditText.getText().toString();
            if (!newDogName.isEmpty()) {
                currentUser = databaseHelper.getSpecificUser(userEmail);
                currentUser.setDogName(newDogName);
                databaseHelper.updateUser(currentUser);
            }

            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(goBackIntent);
        }
    }
}
