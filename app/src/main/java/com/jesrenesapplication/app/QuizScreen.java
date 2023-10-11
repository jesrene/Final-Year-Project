package com.jesrenesapplication.app;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity
import androidx.fragment.app.FragmentManager;


public class QuizScreen extends AppCompatActivity {

    Button imReady;
    Button backButton;

    @SuppressLint("WrongViewCast")
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
                Intent intent = new Intent(QuizScreen.this, QuizLogic.class);
                startActivity(intent);
            }
        });

//// Find the backToRewards LinearLayout by its ID within the XML layout
//        backButton = findViewById(R.id.backToRewards);
//
//// Set an OnClickListener to handle button clicks
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(QuizScreen.this, NavBar.class);
//                startActivity(intent);
//            }
//        });


    }

    // Add quiz-related logic here
}