package com.jesrenesapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LogInScreen extends AppCompatActivity {
    EditText emailEditText;
    EditText passwordEditText;
    Button logInButton;



    private User getUserByEmail(String email) {
        // Replace this with your actual data retrieval logic
        List<User> userList = getUsersFromSomeSource(); // Replace with your data source

        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user; // Found a matching user
            }
        }

        return null; // User not found
    }

    // This is just a placeholder method; you should replace it with your actual data source retrieval logic
    private List<User> getUsersFromSomeSource() {
        // Example: You can return a list of users stored statically for testing purposes
        List<User> userList = new ArrayList<>();
        // Add user objects to the list
        userList.add(new User("user1@example.com", "1"));
        userList.add(new User("user2@example.com", "2"));
        userList.add(new User("user3@example.com", "3"));
        return userList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        emailEditText = findViewById(R.id.editEmail);
        passwordEditText = findViewById(R.id.editPassword);
        logInButton = findViewById(R.id.btnLogIn);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LogInScreen.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Here, you need to implement logic to retrieve the user by email
                User user = getUserByEmail(email);

                if (user != null && user.getPassword().equals(password)) {
                    // Login successful
                    Toast.makeText(LogInScreen.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Start the home activity or fragment
                    Intent intent = new Intent(LogInScreen.this, NavBar.class);
                    startActivity(intent);
                } else {
                    // Login failed
                    Toast.makeText(LogInScreen.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}