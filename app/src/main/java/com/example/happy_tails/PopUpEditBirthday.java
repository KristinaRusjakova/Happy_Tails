package com.example.happy_tails;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class PopUpEditBirthday extends AppCompatActivity implements View.OnClickListener {
    private final Calendar dogBirthdayCalendar = Calendar.getInstance();
    private final Activity activity = PopUpEditBirthday.this;

    private TextInputLayout birthdayInputLayout;
    private TextInputEditText birthdayEditText;
    private Button saveButton, cancelButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_birthday);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initObjects();
        initListeners();
    }

    /**
     * Initialize UI components.
     */
    private void initViews() {
        adjustPopUpWindowSize();

        birthdayInputLayout = findViewById(R.id.PopUpEditBirthdayTextInputLayout);
        birthdayEditText = findViewById(R.id.PopUpEditBirthdayTextInputEditText);
        saveButton = findViewById(R.id.PopUpBirthdaySaveButton);
        cancelButton = findViewById(R.id.PopUpBirthdayCancelButton);

        setupDatePicker();
    }

    /**
     * Adjusts the pop-up window size dynamically.
     */
    private void adjustPopUpWindowSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * 0.8);
        int height = (int) (dm.heightPixels * 0.4);
        getWindow().setLayout(width, height);
    }

    /**
     * Sets up a modern Material Date Picker.
     */
    private void setupDatePicker() {
        birthdayEditText.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Birthday")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                dogBirthdayCalendar.setTimeInMillis(selection);
                updateDateLabel();
            });
        });
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
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    /**
     * Handles button click events.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.PopUpBirthdayCancelButton) {
            navigateBackToEditProfile();
        }
        else if (v.getId() == R.id.PopUpBirthdaySaveButton) {
            saveBirthdayAndNavigateBack();
        }
        }

    /**
     * Navigates back to the Edit Profile screen while retaining the user's email.
     */
    private void navigateBackToEditProfile() {
        String userEmail = getUserEmailFromIntent();
        if (userEmail != null) {
            Intent intent = new Intent(activity, EditProfileInformationMainLoginArea.class);
            intent.putExtra("ownersEmailForPassOn", userEmail);
            startActivity(intent);
        }
    }

    /**
     * Saves the new birthday to the database and navigates back to the Edit Profile screen.
     */
    private void saveBirthdayAndNavigateBack() {
        String userEmail = getUserEmailFromIntent();
        if (userEmail != null) {
            String newBirthday = birthdayEditText.getText().toString();
            if (!newBirthday.isEmpty()) {
                currentUser = databaseHelper.getSpecificUser(userEmail);
                currentUser.setBirthday(newBirthday);
                databaseHelper.updateUser(currentUser);
            }

            navigateBackToEditProfile();
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
     * Updates the date format in the EditText.
     */
    private void updateDateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        birthdayEditText.setText(sdf.format(dogBirthdayCalendar.getTime()));
    }
}
