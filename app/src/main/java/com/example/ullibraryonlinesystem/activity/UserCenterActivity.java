package com.example.ullibraryonlinesystem.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.fragment.UserCenterFragment;

public class UserCenterActivity extends AppCompatActivity {
    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);


        // 只有当之前没有实例状态保存时，才添加 UserCenterFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UserCenterFragment())
                    .commit();
        }

    }




}
