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

        // Initialize views
        imReady = view.findViewById(R.id.btnImReady);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.click);

        // Handle click event for "Im Ready" button
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
        backButton = view.findViewById(R.id.backToRewards);

        // Handle click event for back button to navigate to RewardsFragment
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRewardsFragment();
            }
        });

        loadUserProfilePicture();
        return view;
    }

    // Method to play sound
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Load user's profile picture
    private void loadUserProfilePicture() {
        // Retrieve the user's profile picture URL from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String         profilePic = preferences.getString("userPhotoUrl", "");

        if (picasso == null) {
            picasso = new Picasso.Builder(requireContext()).build();
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

    // Navigate to RewardsFragment
    private void navigateToRewardsFragment() {
        RewardsFragment rewardsFragment = new RewardsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, rewardsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

