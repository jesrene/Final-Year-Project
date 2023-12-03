package com.jesrenesapplication.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class QuizLogic extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button nextQuesBtn;
    ImageView imageProfilePicture;

    private Picasso picasso;
    private MediaPlayer mediaPlayer;


    int score = 0;
    int totalQuestions = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen7_quiz_trivia);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        nextQuesBtn = findViewById(R.id.submit_btn);

        imageProfilePicture = findViewById(R.id.imageProfilePicture);
        mediaPlayer = MediaPlayer.create(this, R.raw.zapsplat_technology_computer_mouse_single_click_001_63274); // Replace "your_sound_file" with the actual file name


        // Initialize and set click listener for the back button
        LinearLayout backButton = findViewById(R.id.backToInformationSnippet);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Handle back button click here, e.g., go back to the previous activity
                finish(); // Finish the current activity to go back
            }
        });

        // Set click listeners for answer buttons
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);

        // Set click listener for the submit button
        nextQuesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                playSound();

                // Check the selected answer and update the score if necessary
                if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                    score++;
                }

                currentQuestionIndex++;
                loadNewQuestion();
            }
        });
        loadUserProfilePicture();
        currentQuestionNumber(); // Update the total questions left text
        loadNewQuestion();
    }

    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }



    @Override
    public void onClick(View view) {
        // Handle selecting an answer

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        selectedAnswer = clickedButton.getText().toString();
        int greyColor = ContextCompat.getColor(this, R.color.deep_orange_200);
        clickedButton.setBackgroundColor(greyColor);
    }

    void currentQuestionNumber() {
        int currentQuestionNumber = currentQuestionIndex + 1; // Adding 1 to convert index to number
        totalQuestionsTextView.setText(currentQuestionNumber + "/10");
    }


    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            finishQuiz();
            return;
        }

        // Reset the background color of all answer buttons to white
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        currentQuestionNumber();

        if (currentQuestionIndex == totalQuestions - 1) { // Assuming the questions are indexed from 0 to 9
            nextQuesBtn.setText("Done!");
        } else {
            nextQuesBtn.setText("Next Question!");
        }
    }


    void finishQuiz() {
        String passStatus = "Score";
        String message;

        if (score == totalQuestions) {
            // If the score is 3 out of 3, reward a voucher
            message = "Score is " + score + " out of " + totalQuestions + ". You have been rewarded a voucher!";
        } else {
            // If the score is not perfect, display the regular score message
            message = "Score is " + score + " out of " + totalQuestions + "Replay and get 10 out of 10 questions correct to get a voucher!";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("Replay", (dialogInterface, i) -> restartQuiz())
                .setNegativeButton("Back to Home", (dialogInterface, i) -> backToHome())
                .setCancelable(false)
                .show();
    }

    void backToHome() {
        // Create an intent to go back to the home screen (or specific fragment)
        Intent intent = new Intent(this, NavBar.class); // Replace with your home activity or target activity
        intent.putExtra("navigate_to_fragment", "QuizScreen"); // Include an identifier for the target fragment

        // Start the intent
        startActivity(intent);

        // Close the current quiz activity
        finish();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    private void loadUserProfilePicture() {
        // Retrieve the user's profile picture URL from SharedPreferences
        SharedPreferences preferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String profilePic = preferences.getString("userPhotoUrl", "");

        // Initialize Picasso (if not already initialized)
        if (picasso == null) {
            picasso = new Picasso.Builder(this).build();
        }

        if (!profilePic.isEmpty()) {
            Uri photoUri = Uri.parse(profilePic);
            picasso.load(photoUri)
                    .transform(new CropCircleTransformation()) // Apply circular transformation
                    .into(imageProfilePicture);
        } else {
            // If profilePic URL is empty, set a default image
            imageProfilePicture.setImageResource(R.drawable.img_profilepic); // Replace with your default image resource
        }
    }
}