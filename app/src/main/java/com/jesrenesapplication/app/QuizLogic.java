package com.jesrenesapplication.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

    public class QuizLogic extends AppCompatActivity implements View.OnClickListener {
        TextView totalQuestionsTextView;
        TextView questionTextView;
        Button ansA, ansB, ansC, ansD;
        Button nextQuesBtn;

        int score = 0;
        int totalQuestion = QuestionAnswer.question.length;
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

            updateTotalQuestionsLeft(); // Update the total questions left text

            loadNewQuestion();
        }

        @Override
        public void onClick(View view) {
            // Handle selecting an answer
            Button clickedButton = (Button) view;
            ansA.setBackgroundColor(Color.WHITE);
            ansB.setBackgroundColor(Color.WHITE);
            ansC.setBackgroundColor(Color.WHITE);
            ansD.setBackgroundColor(Color.WHITE);

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLUE);
        }

        void updateTotalQuestionsLeft() {
            int questionsLeft = totalQuestion - currentQuestionIndex;
            totalQuestionsTextView.setText("Total Questions Left: " + questionsLeft);
        }

        void loadNewQuestion() {
            if (currentQuestionIndex == totalQuestion) {
                finishQuiz();
                return;
            }

            questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
            ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
            ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
            ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
            ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
        }

        void finishQuiz() {
            String passStatus = "YAY";

            new AlertDialog.Builder(this)
                    .setTitle(passStatus)
                    .setMessage("Score is " + score + " out of " + totalQuestion)
                    .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                    .setCancelable(false)
                    .show();
        }

        void restartQuiz() {
            score = 0;
            currentQuestionIndex = 0;
            loadNewQuestion();
        }
    }