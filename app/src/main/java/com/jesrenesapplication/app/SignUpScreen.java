package com.jesrenesapplication.app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity
import androidx.appcompat.widget.AppCompatButton;


public class SignUpScreen extends AppCompatActivity {

    Button signUp;
    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen); // Replace with your XML layout file

        signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpScreen.this, CreateAccountScreen.class);
                startActivity(intent);
            }
        });

        logIn = findViewById(R.id.btnLogIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpScreen.this, LogInScreen.class);
                startActivity(intent);
            }
        });


        // Add quiz-related logic here
    }
}