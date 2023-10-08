package com.jesrenesapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MentalStateDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_state_details_screen); // Replace with your XML layout file

        LinearLayout backToSummary = findViewById(R.id.backToSummary);

        backToSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MentalStateDetails.this, NavBar.class);
                startActivity(intent);

            }
        });
    }
}