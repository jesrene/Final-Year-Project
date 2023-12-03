package com.jesrenesapplication.app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class QuizScreen extends Fragment {

    Button imReady;
    LinearLayout backButton;
    ImageView imageProfilePicture;

    private Picasso picasso;
    private MediaPlayer mediaPlayer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen6_information_snippet, container, false);

        // Find the btnImReady button by its ID within the fragment_quiz_screen.xml layout
        imReady = view.findViewById(R.id.btnImReady);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.zapsplat_technology_computer_mouse_single_click_001_63274);




        // Set an OnClickListener to handle button clicks
        imReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                // Create an Intent to start the QuizLogic activity
                Intent intent = new Intent(getActivity(), QuizLogic.class);
                startActivity(intent);

            }
        });

        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);


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
        loadUserProfilePicture();
        return view;
    }


    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }


    private void loadUserProfilePicture() {
        // Retrieve the user's profile picture URL from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String         profilePic = preferences.getString("userPhotoUrl", "");

        // Initialize Picasso (if not already initialized)
        if (picasso == null) {
            picasso = new Picasso.Builder(requireContext()).build();
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
