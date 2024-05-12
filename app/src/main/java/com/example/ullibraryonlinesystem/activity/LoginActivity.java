package com.example.ullibraryonlinesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.db.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the control
        dbHelper = new DBHelper(this);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_pwd);
        Button loginButton = findViewById(R.id.btnLogin);
        findViewById(R.id.btnRegister);
        Button registerButton;

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the entered credentials match the admin credentials
            if (email.equals("admin@managementmail.ul.ie") && password.equals("123456")) {
                startActivity(new Intent(LoginActivity.this, AdminCenterActivity.class));
            } else {
                // Check user verification
                if (dbHelper.checkUser(email, password)) {
                    startActivity(new Intent(LoginActivity.this, UserCenterActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton = findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(view -> {
            // display the login page
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}