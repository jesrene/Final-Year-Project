package com.jesrenesapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity

public class ScoreScreen extends AppCompatActivity {
    Button viewVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen); // Replace with your XML layout file

        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
        viewVoucher = findViewById(R.id.btnViewVoucher);

        // Set an OnClickListener to handle button clicks
        viewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreScreen.this, VoucherScreen.class);
                startActivity(intent);
            }
        });
    }
}