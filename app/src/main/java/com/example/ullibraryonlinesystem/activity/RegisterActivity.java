package com.example.ullibraryonlinesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ullibraryonlinesystem.R;
import com.example.ullibraryonlinesystem.db.DBHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final int RC_SIGN_IN = 9001;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private EditText majorEditText;
    private DBHelper dbHelper;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth and configure Google Sign-In
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser, false);
        }

        Button googleRegButton = findViewById(R.id.btnGoogleReg);
        googleRegButton.setOnClickListener(view -> signIn());

        dbHelper = new DBHelper(this);
        emailEditText = findViewById(R.id.registerEmail);
        passwordEditText = findViewById(R.id.registerPassword);
        nameEditText = findViewById(R.id.registerName);
        majorEditText = findViewById(R.id.registerMajor);
        Button registerButton = findViewById(R.id.btnRegisterUser);

        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String major = majorEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || major.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "There are unfilled options, please complete them.", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.checkEmailExists(email)) {
                    Toast.makeText(RegisterActivity.this, "Email is already used. Please use a different email.", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addUser(email, password, name, major);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button backButton = findViewById(R.id.RegBackButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void signIn() {
        Log.d(TAG, "Starting Google Sign-In");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Log.d(TAG, "Google Sign-In successful, authenticating with Firebase");
                    firebaseAuthWithGoogle(account.getIdToken());
                } else {
                    Log.w(TAG, "Google Sign-In failed: account is null");
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(RegisterActivity.this, "Google sign in failed: " + e.getStatusCode(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + idToken);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            updateUI(user, isNewUser);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null, false);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user, boolean isNewUser) {
        if (user != null) {
            Toast.makeText(this, "Welcome, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            Intent intent;
            if (isNewUser) {
                // Navigate to registration completion or additional info activity
                intent = new Intent(RegisterActivity.this, UserCenterActivity.class); // Change to appropriate activity
            } else {
                // Navigate to the main or home activity for existing users
                intent = new Intent(RegisterActivity.this, UserCenterActivity.class); // Change to appropriate activity
            }
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "No user is currently signed in.");
        }
    }
}
