package com.jesrenesapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity

public class Quiz2Screen extends AppCompatActivity {
    Button questionDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_2_screen); // Replace with your XML layout file

        questionDone = findViewById(R.id.btnDone);
        questionDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Quiz2Screen.this, ScoreScreen.class);
                startActivity(intent);
            }
        });


    }

    // Add quiz-related logic here
}