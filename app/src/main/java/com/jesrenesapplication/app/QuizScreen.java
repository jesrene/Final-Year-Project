package com.jesrenesapplication.app;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity; // Make sure to import AppCompatActivity
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class QuizScreen extends Fragment {

    Button imReady;
    LinearLayout backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_screen, container, false);

        // Find the btnImReady button by its ID within the fragment_quiz_screen.xml layout
        imReady = view.findViewById(R.id.btnImReady);

        // Set an OnClickListener to handle button clicks
        imReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the QuizLogic activity
                Intent intent = new Intent(getActivity(), QuizLogic.class);
                startActivity(intent);
            }
        });

        // Find the backToRewards button by its ID within the fragment_quiz_screen.xml layout
        backButton = view.findViewById(R.id.backToRewards);

        // Set an OnClickListener to handle button clicks
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the fragment you want to navigate to
                RewardsFragment rewardsFragment = new RewardsFragment();

                // Get the FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.container, rewardsFragment);

                // Add the transaction to the back stack (optional)
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        return view;
    }
}
