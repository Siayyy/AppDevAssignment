package com.example.ullibraryonlinesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.db.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText; // change to email
    private EditText passwordEditText;
    private RadioGroup loginTypeRadioGroup;
    private Button loginButton;
    private Button registerButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the control
        dbHelper = new DBHelper(this);
        emailEditText = findViewById(R.id.login_email); // ID change to email
        passwordEditText = findViewById(R.id.login_pwd);
        loginTypeRadioGroup = findViewById(R.id.radioGroup);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedId = loginTypeRadioGroup.getCheckedRadioButtonId();  // Gets the selected Radio Button ID

                if (selectedId == R.id.radioButtonAdmin) {
                    // Check administrator verification
                    if (email.equals("admin@managementmail.ul.ie") && password.equals("123456")) {
                        startActivity(new Intent(LoginActivity.this, AdminCenterActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedId == R.id.radioButtonUser) {
                    // Check user verification
                    if (dbHelper.checkUser(email, password)) {
                        startActivity(new Intent(LoginActivity.this, UserCenterActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please select your login type", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton = findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display the login page
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

