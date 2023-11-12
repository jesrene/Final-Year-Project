package com.jesrenesapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class VoucherScreen extends Fragment {

    LinearLayout backButton;
    ImageView imageProfilePicture;

    private Picasso picasso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voucher_screen, container, false);

        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);

        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
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

