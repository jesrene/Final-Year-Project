package com.jesrenesapplication.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GetStartedScreen extends AppCompatActivity {

    private static final String TAG = "GetStartedScreen";

    Button getStarted;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);

        getStarted = findViewById(R.id.btnGetStarted);
        mediaPlayer = MediaPlayer.create(this, R.raw.zapsplat_technology_computer_mouse_single_click_001_63274);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                Intent intent = new Intent(GetStartedScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });
    }

    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "Sound completed playing");
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e(TAG, "Error occurred while playing sound. Error code: " + what);
                    return false;
                }
            });

            mediaPlayer.start();
        } else {
            Log.e(TAG, "MediaPlayer is null");
        }
    }

}
