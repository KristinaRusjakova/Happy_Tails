package com.example.happy_tails;

import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;

public class SignUpActivityPart1 extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = SignUpActivityPart1.this;

    private TextInputLayout signUpDogOwnersFullNameTextInputLayout;
    private TextInputLayout signUpOwnersEmailTextInputLayout;
    private TextInputLayout signUpOwnersPasswordTextInputLayout;
    private TextInputLayout signUpOwnersConfirmPasswordTextInputLayout;
    private TextView loginRedirect;

    private Spinner signUpCitySpinner;

    private TextInputEditText signUpOwnersFullNameTextInputEditText;
    private TextInputEditText signUpOwnersEmailTextInputEditText;
    private TextInputEditText signUpOwnersPasswordTextInputEditText;
    private TextInputEditText signUpOwnersConfirmPasswordTextInputEditText;

    private Button signUpNextPageButton;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_part1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initListener();
        initObjects();

        loginRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivityPart1.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * This method is to initialize Views to be used
     */
    private void initViews() {
        // TextInputLayouts
        signUpDogOwnersFullNameTextInputLayout = findViewById(R.id.SignUpDogOwnersFullNameTextInputLayout);
        signUpOwnersEmailTextInputLayout = findViewById(R.id.SignUpOwnersEmailTextInputLayout);
        signUpOwnersPasswordTextInputLayout = findViewById(R.id.SignUpOwnersPasswordTextInputLayout);
        signUpOwnersConfirmPasswordTextInputLayout = findViewById(R.id.SignUpOwnersConfirmPasswordTextInputLayout);

        // TextInputEditTexts
        signUpOwnersFullNameTextInputEditText = findViewById(R.id.SignUpOwnersFullNameTextInputEditText);
        signUpOwnersEmailTextInputEditText = findViewById(R.id.SignUpOwnersEmailTextInputEditText);
        signUpOwnersPasswordTextInputEditText = findViewById(R.id.SignUpOwnersPasswordTextInputEditText);
        signUpOwnersConfirmPasswordTextInputEditText = findViewById(R.id.SignUpOwnersConfirmPasswordTextInputEditText);

        // Button
        signUpNextPageButton = findViewById(R.id.SignUpNextPageButton);

        // SPINNER ACTION FOR CITY SELECTOR
        signUpCitySpinner = findViewById(R.id.SignUpCitySpinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        signUpCitySpinner.setAdapter(staticAdapter);

        //loginRedirect
        loginRedirect = findViewById(R.id.loginRedirect);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListener() {
        signUpNextPageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SignUpNextPageButton) {
            checkInputValidation();
        }
    }

    private void checkInputValidation() {
        if (!inputValidation.isInputEditTextFilled(signUpOwnersFullNameTextInputEditText, signUpDogOwnersFullNameTextInputLayout, getString(R.string.SignUpErrorMessageOwnersName))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(signUpOwnersEmailTextInputEditText, signUpOwnersEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(signUpOwnersEmailTextInputEditText, signUpOwnersEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(signUpOwnersPasswordTextInputEditText, signUpOwnersPasswordTextInputLayout, getString(R.string.SignUpErrorMessagePassword))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(signUpOwnersPasswordTextInputEditText, signUpOwnersConfirmPasswordTextInputEditText,
                signUpOwnersConfirmPasswordTextInputLayout, getString(R.string.SignUpErrorMessagePasswordMatch))) {
            return;
        }
        if (!databaseHelper.checkUser(signUpOwnersEmailTextInputEditText.getText().toString().trim())) {
            // setting up login button redirect to LoginActivity.class
            signUpNextPageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signUpPage2Intent = new Intent(getApplicationContext(), SignUpActivityPart2.class);

                    // pass the input values to next page for registration
                    String ownerName = signUpOwnersFullNameTextInputEditText.getText().toString();
                    signUpPage2Intent.putExtra("uniqueOwnerName", ownerName);

                    String ownerEmail = signUpOwnersEmailTextInputEditText.getText().toString();
                    signUpPage2Intent.putExtra("uniqueOwnerEmail", ownerEmail);

                    String ownerCity = signUpCitySpinner.getSelectedItem().toString();
                    signUpPage2Intent.putExtra("uniqueOwnerCity", ownerCity);

                    String ownerPassword = signUpOwnersPasswordTextInputEditText.getText().toString();
                    signUpPage2Intent.putExtra("uniqueOwnerPassword", ownerPassword);

                    startActivity(signUpPage2Intent);
                    emptyInputEditText();
                }
            });
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(signUpOwnersEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmailExists), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        signUpOwnersFullNameTextInputEditText.setText(null);
        signUpOwnersEmailTextInputEditText.setText(null);
        signUpOwnersPasswordTextInputEditText.setText(null);
        signUpOwnersConfirmPasswordTextInputEditText.setText(null);
    }
}
