package com.example.easygo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextNewPassword;
    private Button buttonUpdatePassword;
    private FirebaseAuth mAuth;
    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonUpdatePassword = findViewById(R.id.buttonUpdatePassword);

        mAuth = FirebaseAuth.getInstance();

        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editTextNewPassword.getText().toString().trim();
                if (!newPassword.isEmpty()) {
                    reauthenticateAndChangePassword(newPassword);
                } else {
                    Toast.makeText(SettingsActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reauthenticateAndChangePassword(final String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Get user's current email and password (for demo purposes)
            String email = user.getEmail();
            String currentPassword = "userCurrentPassword"; // Replace with the actual current password

            // Create credentials
            AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

            // Reauthenticate the user
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Re-authentication succeeded, update password
                        updatePassword(newPassword);
                    } else {
                        Toast.makeText(SettingsActivity.this, "Re-authentication failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Re-authentication failed", task.getException());
                    }
                }
            });
        } else {
            Toast.makeText(this, "No authenticated user", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePassword(String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Password update failed", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Password update failed", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "No authenticated user", Toast.LENGTH_SHORT).show();
        }
    }
}
