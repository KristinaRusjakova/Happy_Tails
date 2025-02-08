package com.example.happy_tails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    // set all variables for input fields and layouts
    private TextInputLayout loginAreaEmailTextInputLayout;
    private TextInputLayout loginAreaPasswordTextInputLayout;

    private TextInputEditText loginAreaEmailTextInputEditText;
    private TextInputEditText loginAreaPasswordTextInputEditText;

    private TextView signupText;
    private Button loginAreaLoginButton;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if ActionBar is present before hiding it
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initListeners();
        initObjects();

        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivityPart1.class);
            startActivity(intent);
        });
    }

    /**
     * This method is to initialize views
     */
    public void initViews() {
        loginAreaEmailTextInputLayout = findViewById(R.id.LoginAreaEmailTextInputLayout);
        loginAreaPasswordTextInputLayout = findViewById(R.id.LoginAreaPasswordTextInputLayout);
        loginAreaEmailTextInputEditText = findViewById(R.id.LoginAreaEmailTextInputEditText);
        loginAreaPasswordTextInputEditText = findViewById(R.id.LoginAreaPasswordTextInputEditText);
        loginAreaLoginButton = findViewById(R.id.LoginAreaLoginButton);
        signupText = findViewById(R.id.signupText);
    }

    /**
     * This method is to initialize listeners
     */
    public void initListeners() {
        loginAreaLoginButton.setOnClickListener(this);
        signupText.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    public void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LoginAreaLoginButton) {
            verifyLogin();
        }
    }

    private void verifyLogin() {
        // Validate email field
        if (!inputValidation.isInputEditTextFilled(loginAreaEmailTextInputEditText, loginAreaEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }

        if (!inputValidation.isInputEditTextEmail(loginAreaEmailTextInputEditText, loginAreaEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }

        // Validate password field
        if (!inputValidation.isInputEditTextFilled(loginAreaPasswordTextInputEditText, loginAreaPasswordTextInputLayout, getString(R.string.SignUpErrorMessagePassword))) {
            return;
        }

        // Check credentials in the database
        if (databaseHelper.checkUser(loginAreaEmailTextInputEditText.getText().toString().trim(),
                loginAreaPasswordTextInputEditText.getText().toString().trim())) {

            Intent loginMainAreaIntent = new Intent(activity, LoginAreaMain.class);
            Snackbar.make(loginAreaLoginButton, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

            String userEmail = loginAreaEmailTextInputEditText.getText().toString().trim();
            loginMainAreaIntent.putExtra("ownersEmailForPassOn", userEmail);
            emptyInputEditText();
            startActivity(loginMainAreaIntent);
        } else {
            Snackbar.make(loginAreaPasswordTextInputLayout, getString(R.string.SignUpErrorMessageIncorrectPassword), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        loginAreaEmailTextInputEditText.setText(null);
        loginAreaPasswordTextInputEditText.setText(null);
    }
}
