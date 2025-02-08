package com.example.happy_tails;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.util.Patterns;

public class InputValidation {

    private final Context context;

    public InputValidation(Context context) {
        this.context = context;
    }

    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        if (textInputEditText == null || textInputLayout == null) return false;

        String value = textInputEditText.getText() != null ? textInputEditText.getText().toString().trim() : "";
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboard(textInputEditText);
            return false;
        } else {
            textInputLayout.setError(null);
        }

        return true;
    }

    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        if (textInputEditText == null || textInputLayout == null) return false;

        String value = textInputEditText.getText() != null ? textInputEditText.getText().toString().trim() : "";
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboard(textInputEditText);
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message) {
        if (textInputEditText1 == null || textInputEditText2 == null || textInputLayout == null) return false;

        String value1 = textInputEditText1.getText() != null ? textInputEditText1.getText().toString().trim() : "";
        String value2 = textInputEditText2.getText() != null ? textInputEditText2.getText().toString().trim() : "";

        if (!value1.equals(value2)) {
            textInputLayout.setError(message);
            hideKeyboard(textInputEditText2);
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }

    private void hideKeyboard(View view) {
        if (view == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
