package com.example.appcartjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcartjava.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEmailForgot;
    private Button mBtnSend;
    private TextView mTvForgotPass;

    // vars
    private SharedPreferences mSharedPreferences;

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mSharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);

        mEmailForgot.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }
        });

        mBtnSend.setOnClickListener(v -> {

            String emailForgot = mEmailForgot.getText().toString();
            if (!emailForgot.isEmpty()) {
                if (mSharedPreferences != null) {
                    String forgotPassword = mSharedPreferences.getString("Password", "");
                    mTvForgotPass.setVisibility(View.VISIBLE);
                    mTvForgotPass.setText(forgotPassword);
                } else {
                    mTvForgotPass.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "This user is not exist", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void initView() {
        mToolbar = findViewById(R.id.topAppBar_forgot_password);
        mEmailForgot = findViewById(R.id.edt_email_forgot);
        mBtnSend = findViewById(R.id.btn_send);
        mTvForgotPass = findViewById(R.id.tv_saved_password);
    }
}