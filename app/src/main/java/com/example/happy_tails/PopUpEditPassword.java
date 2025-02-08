package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PopUpEditPassword extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = PopUpEditPassword.this;

    private TextInputLayout popUpEditPasswordTextInputLayout;
    private TextInputLayout popUpEditPasswordConfirmPasswordTextInputLayout;

    private TextInputEditText popUpEditPasswordTextInputEditText;
    private TextInputEditText popUpEditPasswordConfirmPasswordTextInputEditText;

    private Button popUpEditPasswordCancelButton;
    private Button popUpEditPasswordSaveButton;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initListener();
        initObjects();
    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews() {
        // Getting display metrics of screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Setting Pop-Up Window smaller than background
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));

        popUpEditPasswordTextInputLayout = findViewById(R.id.PopUpEditPasswordTextInputLayout);
        popUpEditPasswordConfirmPasswordTextInputLayout = findViewById(R.id.PopUpEditPasswordConfirmPasswordTextInputLayout);
        popUpEditPasswordTextInputEditText = findViewById(R.id.PopUpEditPasswordTextInputEditText);
        popUpEditPasswordConfirmPasswordTextInputEditText = findViewById(R.id.PopUpEditPasswordConfirmPasswordTextInputEditText);
        popUpEditPasswordCancelButton = findViewById(R.id.PopUpEditPasswordCancelButton);
        popUpEditPasswordSaveButton = findViewById(R.id.PopUpEditPasswordSaveButton);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
        inputValidation = new InputValidation(activity);
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener() {
        popUpEditPasswordCancelButton.setOnClickListener(this);
        popUpEditPasswordSaveButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen to click events
     *
     * @param v The clicked view
     */
    @Override
    public void onClick(View v) {
        Intent previousEditProfileInformationIntent = getIntent();
        String userEmail = null;

        if (previousEditProfileInformationIntent != null && previousEditProfileInformationIntent.getExtras() != null) {
            userEmail = previousEditProfileInformationIntent.getStringExtra("ownersEmailForPassOn");
        }

        if (v.getId() == R.id.PopUpEditPasswordCancelButton) {
            // Intent for previous class
            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            if (userEmail != null) {
                goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            }
            startActivity(goBackIntent);
        } else if (v.getId() == R.id.PopUpEditPasswordSaveButton) {
            // Getting user input
            String newPassword1 = popUpEditPasswordTextInputEditText.getText().toString().trim();
            String newPassword2 = popUpEditPasswordConfirmPasswordTextInputEditText.getText().toString().trim();

            // Ensure fields are not empty
            if (!newPassword1.isEmpty() && !newPassword2.isEmpty()) {
                if (!inputValidation.isInputEditTextMatches(popUpEditPasswordTextInputEditText,
                        popUpEditPasswordConfirmPasswordTextInputEditText,
                        popUpEditPasswordConfirmPasswordTextInputLayout,
                        getString(R.string.SignUpErrorMessagePasswordMatch))) {
                    return;
                } else if (userEmail != null) {
                    currentUser = databaseHelper.getSpecificUser(userEmail);
                    currentUser.setPassword(newPassword1);
                    databaseHelper.updateUser(currentUser);
                }
            }
            // Intent for previous class
            Intent goBackIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            if (userEmail != null) {
                goBackIntent.putExtra("ownersEmailForPassOn", userEmail);
            }
            startActivity(goBackIntent);
        }
    }
    }
