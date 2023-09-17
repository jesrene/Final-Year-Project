package com.jesrenesapplication.app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity




public class LogInScreen extends AppCompatActivity {
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen); // Replace with your XML layout file

        logInButton = findViewById(R.id.btnLogInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInScreen.this, NavBar.class);
                startActivity(intent);
            }
        });

    }
}