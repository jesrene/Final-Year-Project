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
    private Picasso picasso;
    ImageView imageProfilePicture;
    private VideoView videoView;
    private MediaPlayer mediaPlayer;

    // Method to play sound
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen5_quiz, container, false);

        // Initialize views
        playGame = view.findViewById(R.id.btnPlayGame);
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.click); // Replace "your_sound_file" with the actual file name
        videoView = view.findViewById(R.id.videoView);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);

        // Set up video playback
        String videoPath = "android.resource://" + "com.jesrenesapplication.app" + "/" + R.raw.trophy; // Replace with your video file
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();

        // Loop the video when it reaches the end
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });


        // Set an OnClickListener to handle button clicks
        playGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                playSound();
                QuizScreen quizScreen = new QuizScreen();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, quizScreen);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        loadUserProfilePicture();
        return view;
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
}
