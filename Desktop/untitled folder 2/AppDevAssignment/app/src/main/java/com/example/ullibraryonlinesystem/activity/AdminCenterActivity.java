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


            // Add AdminCenterFragment only if no instance state is previously saved
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AdminCenterFragment())
                        .commit();
            }

        }
}
