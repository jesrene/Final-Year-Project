package com.jesrenesapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EditProfile extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 1;

    LinearLayout backButton;
    ImageView imageProfilePicture;
    private Picasso picasso;


    private void setUserDetails(View view) {
        // Retrieve the user's email from the arguments
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String email = getArguments() != null ? getArguments().getString("userEmail") : "";

        email = preferences.getString("userEmail", "");
        Log.d("EditProfileFragment", "User Email: " + email);

        // String name = getArguments() != null ? getArguments().getString("userName") : "";
        String name = preferences.getString("userName", "");
        Log.d("EditProfileFragment", "User Name: " + name);

        TextView emailTextView = view.findViewById(R.id.txtEmail);
        emailTextView.setText(email);

        TextView nameTextView = view.findViewById(R.id.txtName);
        nameTextView.setText(name);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.screen9_edit_profile, container, false);
        setUserDetails(view);

        backButton = view.findViewById(R.id.backToProfile);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the fragment you want to navigate to
                ProfileFragment profileFragment = new ProfileFragment();

                // Get the FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.container, profileFragment);

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

