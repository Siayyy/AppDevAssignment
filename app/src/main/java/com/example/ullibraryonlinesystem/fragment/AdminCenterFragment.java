package com.example.ullibraryonlinesystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.activity.LoginActivity;

public class AdminCenterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_center, container, false);

        TextView logoutTextView = view.findViewById(R.id.admin_center_logout);
        logoutTextView.setOnClickListener(v -> {
            logout();
        });

        return view;
    }

    private void logout() {
        // Clear admin session or token
        // Navigate back to the login screen
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

}