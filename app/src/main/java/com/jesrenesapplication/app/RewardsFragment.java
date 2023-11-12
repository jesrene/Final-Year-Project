package com.jesrenesapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class RewardsFragment extends Fragment {
    Button playGame;
    Button viewVoucher;
    private Picasso picasso;
    ImageView imageProfilePicture;
    private VideoView videoView;

    private MediaPlayer mediaPlayer;


    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
        playGame = view.findViewById(R.id.btnPlayGame);
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.zapsplat_technology_computer_mouse_single_click_001_63274); // Replace "your_sound_file" with the actual file name
        videoView = view.findViewById(R.id.videoView);


        // Set the video path
        String videoPath = "android.resource://" + "com.jesrenesapplication.app" + "/" + R.raw.trophy; // Replace with your video file

        // Set the video URI
        Uri videoUri = Uri.parse(videoPath);

        // Set the video URI to the VideoView
        videoView.setVideoURI(videoUri);

        // Start the video
        videoView.start();

        // Set completion listener to loop the video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Loop the video when it reaches the end
                videoView.start();
            }
        });



        // Set an OnClickListener to handle button clicks
        playGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                playSound();
                // Create an instance of the fragment you want to navigate to
                QuizScreen quizScreen = new QuizScreen();

                // Get the FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.container, quizScreen);

                // Add the transaction to the back stack (optional)
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);

        // Find the btnViewVoucher button by its ID within the fragment_rewards.xml layout
        viewVoucher = view.findViewById(R.id.btnViewVoucher);


        // Set an OnClickListener to handle button clicks
        viewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                // Create an instance of the fragment you want to navigate to
                VoucherScreen voucherScreen = new VoucherScreen();

                // Get the FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.container, voucherScreen);

                // Add the transaction to the back stack (optional)
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        loadUserProfilePicture();
        return view;
    }


    private void loadUserProfilePicture() {
        // Retrieve the user's profile picture URL from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String profilePic = preferences.getString("userPhotoUrl", "");

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

