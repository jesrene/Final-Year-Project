package com.jesrenesapplication.app;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class SignUpScreen extends AppCompatActivity {

    // Declare variables
    ImageView imageProfilePicture;
    LinearLayout signInWithGoogleButton;
    private GoogleSignInClient mGoogleSignInClient;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2_sign_in);

        // Initialize views and media player
        signInWithGoogleButton = findViewById(R.id.btnSignInGoogle);
        mediaPlayer = MediaPlayer.create(this, R.raw.click);

        // Configure Google sign-in options
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Create a GoogleSignInClient with the specified options
        mGoogleSignInClient = GoogleSignIn.getClient(this, options);

        // Set onClickListener for the "Sign In with Google" button
        signInWithGoogleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playSound();
                    signOutAndSignInWithGoogle();
                    loadGoogleProfilePicture();
                }
            });
        }

    // Retrieve the GoogleSignInAccount
    private void loadGoogleProfilePicture() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Get the user's profile picture Uri
        if (account != null) {
            Uri photoUri = account.getPhotoUrl();

            if (photoUri != null) {
                Picasso.get().load(photoUri).into(imageProfilePicture);
                Log.d("ProfileImageDebug", "Loaded profile image: " + photoUri.toString());
            }
        }
    }

    // Sign out the current user before signing in with Google
    private void signOutAndSignInWithGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    // After signing out, initiate the sign-in process
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                } else {
                    Toast.makeText(SignUpScreen.this, "Sign-out failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Sign-out failed: " + task.getException());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private static final int RC_SIGN_IN = 123;

    // After a successful Google Sign-In, save the user's data
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                String email = account.getEmail();
                String userName = account.getDisplayName();
                String personId = account.getId();
                Uri photoUri = account.getPhotoUrl();

                // Save the user data in SharedPreferences
                saveUserData(email, userName, personId, photoUri);

            } else {
                Toast.makeText(this, "Sign-in failed. Please try again.", Toast.LENGTH_SHORT).show();
            }

        Intent intent = new Intent(this, NavBar.class);
        intent.putExtra("googleSignInAccount", account); // Pass the GoogleSignInAccount as an extra to the HomeFragment
        startActivity(intent);
    }

    // Method to play sound
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Define the updateUI method
    private void saveUserData(String userEmail, String userName, String personId, Uri photoUri) {
        // Use SharedPreferences to store the user's data
        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userEmail", userEmail);
        editor.putString("userName", userName);
        editor.putString("userPhotoUrl", photoUri != null ? photoUri.toString() : "");
        editor.apply();
    }
}
