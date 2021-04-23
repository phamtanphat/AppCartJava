package com.example.appcartjava.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appcartjava.R;
import com.example.appcartjava.models.Credentials;

public class CreateNewUserActivity extends AppCompatActivity {

    public static Credentials mCredentials;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Toolbar mToolbar;
    private EditText mEdtFullName, mEdtAccount, mEdtPassword, mEdtEmail, mEdtPhoneNumber, mEdtAddress;
    private Button mCreateButton;

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        initView();

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        mEdtFullName.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mEdtAccount.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mEdtPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mEdtEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mEdtPhoneNumber.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mEdtAddress.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mSharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mCreateButton.setOnClickListener(v -> {
            String fullName = mEdtFullName.getText().toString();
            String account = mEdtAccount.getText().toString();
            String password = mEdtPassword.getText().toString();
            String email = mEdtEmail.getText().toString();
            String phoneNumber = mEdtPhoneNumber.getText().toString();
            String address = mEdtAddress.getText().toString();
            if (!fullName.isEmpty()
                    && !account.isEmpty()
                    && !password.isEmpty()
                    && !email.isEmpty()
                    && !phoneNumber.isEmpty()
                    && !address.isEmpty()

            ) {
                // Store the credentials
                mEditor.putString("Username", email);
                mEditor.putString("Password", password);
                // Commits the changes and adds them to the file
                mEditor.apply();

                createNewUser(email, password);
            }

        });
    }

    private void createNewUser(String userName, String password) {
        if (password.length() < 8) {
            Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
        } else {
            mCredentials = new Credentials(userName, password);
            Toast.makeText(this, "Create successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.topAppBar_create_new_user);

        mEdtFullName = findViewById(R.id.edt_full_name);
        mEdtAccount = findViewById(R.id.edt_account);
        mEdtPassword = findViewById(R.id.edt_password_create_new_user);
        mEdtEmail = findViewById(R.id.edt_email_create_new_user);
        mEdtPhoneNumber = findViewById(R.id.edt_phone_number);
        mEdtAddress = findViewById(R.id.edt_address);
        mCreateButton = findViewById(R.id.btn_create);
    }
}