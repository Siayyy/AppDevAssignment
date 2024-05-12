package com.example.ullibraryonlinesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.databinding.ActivityLoginBinding;
import com.example.ullibraryonlinesystem.db.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText; // 改成 email
    private EditText passwordEditText;
    private RadioGroup loginTypeRadioGroup;
    private Button loginButton;
    private Button registerButton;
    private DBHelper dbHelper;
   private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle click, go to register screen
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        // 初始化控件
        dbHelper = new DBHelper(this);
        emailEditText = findViewById(R.id.login_email); // ID 也变成 email
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

                int selectedId = loginTypeRadioGroup.getCheckedRadioButtonId();  // 获取选择的 Radio Button ID

                if (selectedId == R.id.radioButtonAdmin) {
                    // 检查管理员凭证
                    if (email.equals("admin@managementmail.ul.ie") && password.equals("123456")) {
                        startActivity(new Intent(LoginActivity.this, AdminCenterActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedId == R.id.radioButtonUser) {
                    // 检查普通用户凭证
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
                // 跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

