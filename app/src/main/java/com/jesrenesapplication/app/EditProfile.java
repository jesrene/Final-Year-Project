package com.jesrenesapplication.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jesrenesapplication.app.R;

public class EditProfile extends AppCompatActivity {

    ProfileFragment profileFragment = new ProfileFragment();


    private static final int REQUEST_CODE_SELECT_IMAGE = 1001; // Define a request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        FrameLayout frameUploadphoto = findViewById(R.id.frameUploadphoto);

        frameUploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to open the gallery for image selection
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }
        });

        LinearLayout backButton = findViewById(R.id.backToProfile); // Initialize backButton here

        // Set an OnClickListener to handle button clicks
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                // Finish the current activity to go back
            }
        });
    }


// Handle the result when an image is selected from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            // The user has selected an image from the gallery
            if (data != null && data.getData() != null) {
                // Retrieve the selected image URI
                Uri selectedImageUri = data.getData();

                // Show a toast message for successful photo upload
                Toast.makeText(this, "Photo uploaded successfully", Toast.LENGTH_SHORT).show();

                // If you want to perform further image processing or upload to a server, you can do so here.
            }
        }

    }
}
