package com.jesrenesapplication.app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity
import androidx.appcompat.widget.AppCompatButton;


public class Quiz1Screen extends AppCompatActivity {

    Button nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_1_screen); // Replace with your XML layout file

        nextQuestion = findViewById(R.id.btnNextQuestion);
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Quiz1Screen.this, Quiz2Screen.class);
                startActivity(intent);
            }
        });

        // Add quiz-related logic here
    }
}