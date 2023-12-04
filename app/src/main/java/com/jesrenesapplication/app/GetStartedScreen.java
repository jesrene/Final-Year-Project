package com.jesrenesapplication.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GetStartedScreen extends AppCompatActivity {
    // Declare variables
    Button getStarted;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen1_get_started);

        // Initialize views and media player
        getStarted = findViewById(R.id.btnGetStarted);
        mediaPlayer = MediaPlayer.create(this, R.raw.click);

        // Set onClickListener for the "Get Started" button
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                Intent intent = new Intent(GetStartedScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });
    }

    // Method to play sound
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
