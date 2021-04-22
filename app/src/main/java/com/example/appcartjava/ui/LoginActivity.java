package com.example.appcartjava.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcartjava.R;
import com.example.appcartjava.models.Credentials;

public class LoginActivity extends AppCompatActivity {

    // ui
    private EditText mUserEditText, mPasswordEditText;
    private Button mCreateNewUser, mForgotPassword, mLogin;
    // var
    private long backPressedTime;
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCbRememberMe;

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        mSharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        if (mSharedPreferences != null) {
            String savedUsername = mSharedPreferences.getString("Username", "");
            String savedPassword = mSharedPreferences.getString("Password", "");

            CreateNewUserActivity.mCredentials = new Credentials(savedUsername, savedPassword);
            if (mSharedPreferences.getBoolean("RememberMeCheckbox", false)) {
                mUserEditText.setText(savedUsername);
                mPasswordEditText.setText(savedPassword);
                mCbRememberMe.setChecked(true);
            }
        }

        mCbRememberMe.setOnClickListener(v -> {
            mEditor.putBoolean("RememberMeCheckbox", mCbRememberMe.isChecked());
            mEditor.apply();
        });

        mUserEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mPasswordEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mCreateNewUser.setOnClickListener(v -> startActivity(new Intent(this, CreateNewUserActivity.class)));

        mForgotPassword.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));

        mLogin.setOnClickListener(v -> {
            String userName = mUserEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            if (!userName.isEmpty() || !password.isEmpty()) {
                validateUser(userName, password);
            } else {
                Toast.makeText(this, "Please enter your username or your password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateUser(String username, String password) {
        if (CreateNewUserActivity.mCredentials.getUsername() != null && CreateNewUserActivity.mCredentials.getPassword() != null) {
            if (username.equals(CreateNewUserActivity.mCredentials.getUsername()) && password.equals(CreateNewUserActivity.mCredentials.getPassword())) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, ListActivity.class));
            } else if (!username.equals(CreateNewUserActivity.mCredentials.getUsername())) {
                Toast.makeText(this, "Your username is not correct", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(CreateNewUserActivity.mCredentials.getPassword())) {
                Toast.makeText(this, "Your password is not correct", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "This user is not exists!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mUserEditText = findViewById(R.id.edt_username);
        mPasswordEditText = findViewById(R.id.edt_password);
        mCreateNewUser = findViewById(R.id.btn_create_new_account);
        mForgotPassword = findViewById(R.id.btn_forgot_password);
        mCbRememberMe = findViewById(R.id.cb_remember_me);
        mLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 >= System.currentTimeMillis()) {
            mToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mToast = Toast.makeText(LoginActivity.this, "Press back again to exit the application", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}