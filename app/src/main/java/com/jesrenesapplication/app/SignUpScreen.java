package com.jesrenesapplication.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

    Button signUp;
    Button logIn;
    ImageView imageProfilepic;

    LinearLayout signInWithGoogleButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        signUp = findViewById(R.id.btnSignUp);
        logIn = findViewById(R.id.btnLogIn);
        signInWithGoogleButton = findViewById(R.id.btnSignInGoogle);

        imageProfilepic = findViewById(R.id.imageProfilepic);


        // Configure sign-in to request the user's ID, email address, and basic profile
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, options);

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignUpScreen.this, CreateAccountScreen.class);
                    startActivity(intent);
                }
            });

            logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignUpScreen.this, LogInScreen.class);
                    startActivity(intent);
                }
            });

            signInWithGoogleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOutAndSignInWithGoogle();
                    loadGoogleProfilePicture();
                }
            });

        }
    private void loadGoogleProfilePicture() {
        // Retrieve the GoogleSignInAccount (you should have this from the sign-in process)
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Get the user's profile picture Uri
        if (account != null) {
            Uri photoUri = account.getPhotoUrl();

            if (photoUri != null) {
                // Use Picasso (or another image loading library) to load and display the image
                Picasso.get().load(photoUri).into(imageProfilepic);
                Log.d("ProfileImageDebug", "Loaded profile image: " + photoUri.toString());
            }
        }
    }
    private void signOutAndSignInWithGoogle() {
        // Sign out the current user before signing in with Google
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    // After signing out, initiate the sign-in process
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                } else {
                    // Handle sign-out error
                    // You can display an error message or handle the error as needed
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private static final int RC_SIGN_IN = 123; // Replace with your request code

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

                Log.d("Sign Up Screen", "User Email: " + email);
                Log.d("Sign Up Screen", "User name: " + userName);
                Log.d("Sign Up Screen", "id: " + personId);

            } else {
                Log.e("Sign Up Screen", "GoogleSignInAccount is null.");
            }

            // Create an intent to navigate to the NavBar activity
            Intent intent = new Intent(this, NavBar.class);

            // Pass the GoogleSignInAccount as an extra to the HomeFragment
            intent.putExtra("googleSignInAccount", account);

            //todo
            EditProfile editProfileFragment = new EditProfile();
//            Bundle args = new Bundle();
//            args.putString("userEmail", email);
//            args.putString("userName", userName);
//            editProfileFragment.setArguments(args);

            // Start the NavBar activity
            startActivity(intent);

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
