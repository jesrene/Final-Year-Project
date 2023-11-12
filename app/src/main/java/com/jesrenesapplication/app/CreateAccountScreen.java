package com.jesrenesapplication.app;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class CreateAccountScreen extends AppCompatActivity {
    EditText fullNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);

        emailEditText = findViewById(R.id.editEmail);
        passwordEditText = findViewById(R.id.editPassword);
        signUpButton = findViewById(R.id.btnSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve email and password from EditText fields
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate input fields
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(CreateAccountScreen.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new User object and save it (e.g., in SharedPreferences or a local database)
                User user = new User(email, password);
                saveUser(user);

                // Show a success message or navigate to the login screen
                Toast.makeText(CreateAccountScreen.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateAccountScreen.this, LogInScreen.class);
                startActivity(intent);
            }
        });
    }

    private void saveUser(User user) {
        // Implement the logic to save the user data (e.g., in SharedPreferences or a local database)
    }
}