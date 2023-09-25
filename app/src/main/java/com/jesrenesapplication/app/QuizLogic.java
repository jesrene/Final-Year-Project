package com.jesrenesapplication.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

public class QuizLogic extends AppCompatActivity implements View.OnClickListener {
        TextView totalQuestionsTextView;
        TextView questionTextView;
        Button ansA, ansB, ansC, ansD;
        Button nextQuesBtn;

        int score = 0;
        int totalQuestions = QuestionAnswer.question.length;
        int currentQuestionIndex = 0;
        String selectedAnswer = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz_1_screen);

            totalQuestionsTextView = findViewById(R.id.total_question);
            questionTextView = findViewById(R.id.question);
            ansA = findViewById(R.id.ansA);
            ansB = findViewById(R.id.ansB);
            ansC = findViewById(R.id.ansC);
            ansD = findViewById(R.id.ansD);
            nextQuesBtn = findViewById(R.id.submit_btn);

            // Set click listeners for answer buttons
            ansA.setOnClickListener(this);
            ansB.setOnClickListener(this);
            ansC.setOnClickListener(this);
            ansD.setOnClickListener(this);

            // Set click listener for the submit button
            nextQuesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check the selected answer and update the score if necessary
                    if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                        score++;
                    }

                    currentQuestionIndex++;
                    loadNewQuestion();
                }
            });

            currentQuestionNumber(); // Update the total questions left text

            loadNewQuestion();
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
            int greyColor = ContextCompat.getColor(this, R.color.gray_300);
            clickedButton.setBackgroundColor(greyColor);
        }

        void currentQuestionNumber() {
            int currentQuestionNumber = currentQuestionIndex + 1; // Adding 1 to convert index to number
            totalQuestionsTextView.setText(currentQuestionNumber + "/3");
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

        }

    void finishQuiz() {
        String passStatus = "Score";
        String message;

        if (score == totalQuestions) {
            // If the score is 3 out of 3, reward a voucher
            message = "Score is " + score + " out of " + totalQuestions + ". You have been rewarded a voucher!";
        } else {
            // If the score is not perfect, display the regular score message
            message = "Score is " + score + " out of " + totalQuestions;
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setNegativeButton("Back to Home", (dialogInterface, i) -> backToHome()) // TODO
                .setCancelable(false)
                .show();
    }

        //TODO
        void backToHome() {
            // Create an intent to go back to the home screen (or specific fragment)
            Intent intent = new Intent(this, RewardsFragment.class); // Replace with your home activity or target activity
            intent.putExtra("navigate_to_fragment", "home_fragment"); // Include an identifier for the target fragment

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
    }