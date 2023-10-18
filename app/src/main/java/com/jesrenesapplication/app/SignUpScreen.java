package com.jesrenesapplication.app;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignUpScreen extends AppCompatActivity {

    Button signUp;
    Button logIn;
    LinearLayout signInWithGoogleButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        signUp = findViewById(R.id.btnSignUp);
        logIn = findViewById(R.id.btnLogIn);
        signInWithGoogleButton = findViewById(R.id.btnSignInGoogle);

        // Configure sign-in to request the user's ID, email address, and basic profile
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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
            }
        });
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
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String email = account.getEmail();
            Uri photoUri = account.getPhotoUrl();

            // Create an intent to navigate to the NavBar activity
            Intent intent = new Intent(this, NavBar.class);

            // Pass user data to the NavBar activity
            intent.putExtra("userEmail", email);
            intent.putExtra("userPhotoUrl", photoUri != null ? photoUri.toString() : "");

            // Start the NavBar activity
            startActivity(intent);

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


    // Define the updateUI method
    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            // The user is signed in, you can perform actions like displaying their name or email.
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();

            // You can now use the user's information in your app or navigate to another activity.
//            Intent intent = new Intent(SignUpScreen.this, LogInScreen.class);
//            intent.putExtra("userEmail", userEmail);
//            startActivity(intent);
        } else {
            // The user is not signed in.
            // You can handle this case by displaying a sign-in button or any other logic.
        }
    }
}


//    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//    if (account != null) {
//        String displayName = account.getDisplayName(); // Retrieve the user's display name (name).
//        String email = account.getEmail();
//        String userId = account.getId();
//        Uri photoUrl = account.getPhotoUrl();
//
//    } else {
//        // The user is not signed in; handle this case accordingly.
//    }
//}