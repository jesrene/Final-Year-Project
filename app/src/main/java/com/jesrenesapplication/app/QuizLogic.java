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

    // Variable declarations
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

        // Find and assign views to respective variables
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        nextQuesBtn = findViewById(R.id.submit_btn);

        imageProfilePicture = findViewById(R.id.imageProfilePicture);
        mediaPlayer = MediaPlayer.create(this, R.raw.click);

        // Initialize and set click listener for the back button
        LinearLayout backButton = findViewById(R.id.backToInformationSnippet);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Finish the current activity to go back
            }
        });

        // Set click listeners for answer buttons to handle user input
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);

        // Set click listener for the next question button
        nextQuesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                playSound();

                // Check the selected answer and update the score if necessary
                if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                    score++; // Increment the score for correct answers
                }

                currentQuestionIndex++; // Move to the next question index
                loadNewQuestion();
            }
        });
        loadUserProfilePicture();
        currentQuestionNumber();
        loadNewQuestion();
    }

    // Method to play sound
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Handle selecting an answer
    @Override
    public void onClick(View view) {
        // Reset the background color of all answer buttons to white
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        // Highlight the clicked answer button and set the selected answer
        Button clickedButton = (Button) view;
        selectedAnswer = clickedButton.getText().toString();
        int greyColor = ContextCompat.getColor(this, R.color.deep_orange_200);
        clickedButton.setBackgroundColor(greyColor);
    }

    // Update the displayed current question number out of the total number of questions
    void currentQuestionNumber() {
        int currentQuestionNumber = currentQuestionIndex + 1; // Adding 1 to convert index to number
        totalQuestionsTextView.setText(currentQuestionNumber + "/10");
    }

    // Load a new question or finish the quiz if all questions are answered
    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            finishQuiz(); // Finish the quiz if all questions are answered
            return;
        }

        // Reset the background color of all answer buttons to white
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        // Load the question and answer options based on the current question index
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        currentQuestionNumber(); // Update the displayed question number

        if (currentQuestionIndex == totalQuestions - 1) { // Assuming the questions are indexed from 0 to 9
            nextQuesBtn.setText("Done!"); // Update button text to "Done" for the last question
        } else {
            nextQuesBtn.setText("Next Question!");
        }
    }

    // Display a congratulatory message upon finishing the quiz
    void finishQuiz() {
        String passStatus = "Congratulations!";
        String message;

        // Score message
        if (score == totalQuestions) {
            message = "YAY! Score is " + score + " out of " + totalQuestions + ". ";
        } else {
            message = "Score is " + score + " out of " + totalQuestions + ". ";
        }

        // Show an alert dialog with quiz results
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("Replay", (dialogInterface, i) -> restartQuiz())
                .setNegativeButton("Back to Home", (dialogInterface, i) -> backToHome())
                .setCancelable(false)
                .show();
    }

    // Navigate back to the home screen using an intent
    void backToHome() {
        Intent intent = new Intent(this, NavBar.class);
        intent.putExtra("navigate_to_fragment", "QuizScreen");
        startActivity(intent);
        finish();
    }

    // Reset the quiz by resetting the score and current question index, then load a new question
    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    // Load user's profile picture
    private void loadUserProfilePicture() {
        // Retrieve the user's profile picture URL from SharedPreferences
        SharedPreferences preferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String         profilePic = preferences.getString("userPhotoUrl", "");

        if (picasso == null) {
            picasso = new Picasso.Builder(this).build();
        }
        if (!profilePic.isEmpty()) {
            Uri photoUri = Uri.parse(profilePic);
            picasso.load(photoUri)
                    .transform(new CropCircleTransformation())
                    .into(imageProfilePicture);
        } else {
            // If profilePic URL is empty, set a default image
            imageProfilePicture.setImageResource(R.drawable.img_profilepic);
        }
    }
}