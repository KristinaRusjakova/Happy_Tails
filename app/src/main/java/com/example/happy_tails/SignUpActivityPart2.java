package com.example.happy_tails;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpActivityPart2 extends AppCompatActivity implements View.OnClickListener {

    private final Calendar dogBirthdayCalendar = Calendar.getInstance();
    private TextInputLayout signUpDogNameTextInputLayout, signUpDogBirthdayTextInputLayout;
    private TextInputEditText signUpDogsNameTextInputEditText, signUpDogsBirthdayTextInputEditText;
    private Spinner signUpDogBreedSpinner, signUpDogGenderSpinner;
    private Button signUpCompleteSignUpButton;
    private ImageView signUpUserImageDogPhoto;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    private Uri photoUri;
    private File photoFile;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_part2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        initListener();
        initObjects();
    }

    private void initViews() {
        signUpDogNameTextInputLayout = findViewById(R.id.SignUpDogNameTextInputLayout);
        signUpDogBirthdayTextInputLayout = findViewById(R.id.SignUpDogBirthdayTextInputLayout);
        signUpDogsNameTextInputEditText = findViewById(R.id.SignUpDogsNameTextInputEditText);
        signUpDogsBirthdayTextInputEditText = findViewById(R.id.SignUpDogsBirthdayTextInputEditText);
        signUpCompleteSignUpButton = findViewById(R.id.SignUpCompleteSignUpButton);
        signUpUserImageDogPhoto = findViewById(R.id.SignUpDogScreenImageView);
        CalendarView signUpDogsBirthdayCalendarView = findViewById(R.id.SignUpDogsBirthdayCalendarView);

        signUpDogsBirthdayTextInputEditText.setOnClickListener(v -> {
            signUpDogsBirthdayCalendarView.setVisibility(View.VISIBLE);
        });

        signUpDogsBirthdayCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dogBirthdayCalendar.set(Calendar.YEAR, year);
            dogBirthdayCalendar.set(Calendar.MONTH, month);
            dogBirthdayCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Format date and set to EditText
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            signUpDogsBirthdayTextInputEditText.setText(dateFormat.format(dogBirthdayCalendar.getTime()));

            // Hide CalendarView after selection
            signUpDogsBirthdayCalendarView.setVisibility(View.GONE);
        });

        // Breed Spinner
        signUpDogBreedSpinner = findViewById(R.id.SignUpDogBreedSpinner);
        ArrayAdapter<CharSequence> breedAdapter = ArrayAdapter.createFromResource(this,
                R.array.dogBreeds_array, android.R.layout.simple_spinner_item);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUpDogBreedSpinner.setAdapter(breedAdapter);

        // Gender Spinner
        signUpDogGenderSpinner = findViewById(R.id.SignUpDogGenderSpinner);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.dogGender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUpDogGenderSpinner.setAdapter(genderAdapter);
    }

    private void initObjects() {
        inputValidation = new InputValidation(this);
        databaseHelper = new DatabaseHelper(this);
        user = new User();
    }

    private void initListener() {
        signUpCompleteSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SignUpCompleteSignUpButton) {
            postDataToSQLite();
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(signUpDogsNameTextInputEditText, signUpDogNameTextInputLayout, getString(R.string.SignUpErrorMessageDogName))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(signUpDogsBirthdayTextInputEditText, signUpDogBirthdayTextInputLayout, getString(R.string.SignUpErrorMessageDogBirthday))) {
            return;
        }

        Intent previousIntent = getIntent();
        user.setOwnerFullName(previousIntent.getStringExtra("uniqueOwnerName"));
        user.setEmail(previousIntent.getStringExtra("uniqueOwnerEmail"));
        user.setCity(previousIntent.getStringExtra("uniqueOwnerCity"));
        user.setPassword(previousIntent.getStringExtra("uniqueOwnerPassword"));
        user.setDogName(signUpDogsNameTextInputEditText.getText().toString().trim());
        user.setDogBreed(signUpDogBreedSpinner.getSelectedItem().toString());
        user.setBirthday(signUpDogsBirthdayTextInputEditText.getText().toString());
        user.setDogGender(signUpDogGenderSpinner.getSelectedItem().toString());
        user.setImagePath(photoUri != null ? photoUri.toString() : "default_image_path");
        user.setDescription("Welcome to " + user.getDogName() + "'s HappyTails Page!");

        databaseHelper.addUser(user);
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        signUpDogsBirthdayTextInputEditText.setText(sdf.format(dogBirthdayCalendar.getTime()));
    }

    public void signUpDogImageTakePhotoMethod(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = File.createTempFile("happy_tails_" + System.currentTimeMillis(), ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraLauncher.launch(intent);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signUpDogImageChoosePhotoMethod(View v) {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhotoIntent.setType("image/*");
        galleryLauncher.launch(pickPhotoIntent);
    }

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    photoUri = result.getData().getData();
                    signUpUserImageDogPhoto.setImageURI(photoUri);
                }
            }
    );

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    signUpUserImageDogPhoto.setImageURI(photoUri);
                }
            }
    );
}
