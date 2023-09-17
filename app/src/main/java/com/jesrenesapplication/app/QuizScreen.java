package com.jesrenesapplication.app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity


public class QuizScreen extends AppCompatActivity {

    Button imReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen); // Replace with your XML layout file

        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
        imReady = findViewById(R.id.btnImReady);

        // Set an OnClickListener to handle button clicks
        imReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizScreen.this, Quiz1Screen.class);
                startActivity(intent);
            }
        });



    }

    // Add quiz-related logic here
}

