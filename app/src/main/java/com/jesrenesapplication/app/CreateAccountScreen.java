package com.jesrenesapplication.app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity

public class CreateAccountScreen extends AppCompatActivity {

    EditText fullNameEditText;
    EditText emailEditText;
    EditText passwordEditText;

    Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);

        // Initialize EditText fields after setContentView
        fullNameEditText = findViewById(R.id.editFullName);
        emailEditText = findViewById(R.id.editEmail);
        passwordEditText = findViewById(R.id.editPassword);


        signUpButton = findViewById(R.id.btnSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input after the button is clicked
                String enteredFullName = fullNameEditText.getText().toString();
                String enteredEmail = emailEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                Log.d("UserInput", "Entered Email: " + enteredEmail);
                Log.d("UserInput", "Entered Password: " + enteredPassword);


                // Now you can use these values for further processing
                // For example, you can send this data to the server or perform local validation
                // Then, you can start the login activity if needed
                Intent intent = new Intent(CreateAccountScreen.this, LogInScreen.class);
                intent.putExtra("email", enteredEmail); // Pass the entered email as an extra
                startActivity(intent);
            }
        });
    }
}

