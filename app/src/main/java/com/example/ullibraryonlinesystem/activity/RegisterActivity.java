package com.example.ullibraryonlinesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.db.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private EditText majorEditText;
    private Button registerButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);
        emailEditText = findViewById(R.id.registerEmail);
        passwordEditText = findViewById(R.id.registerPassword);
        nameEditText = findViewById(R.id.registerName);
        majorEditText = findViewById(R.id.registerMajor);
        registerButton = findViewById(R.id.btnRegisterUser);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String major = majorEditText.getText().toString().trim();

                // 检查是否有未填写的项
                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || major.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "There are unfilled options, please complete them.", Toast.LENGTH_SHORT).show();
                } else {
                    // 将数据插入数据库
                    // dbHelper.addUser(email, password, name, major); // 你需要在 DBHelper 中实现这个方法
                    // 注册成功后返回登录页面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前的 Activity
                }
            }
        });
    }
}

