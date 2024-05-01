package com.example.ullibraryonlinesystem.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.fragment.AdminCenterFragment;
import com.example.ullibraryonlinesystem.fragment.UserCenterFragment;

public class AdminCenterActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_center);


            // 只有当之前没有实例状态保存时，才添加 UserCenterFragment
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AdminCenterFragment())
                        .commit();
            }

        }
}
