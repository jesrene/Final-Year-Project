package com.jesrenesapplication.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class ProfileFragment extends Fragment {

    LinearLayout editProfile;
    Button logOutButton;
    ImageView imageProfilepic;

    private Picasso picasso;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the EditProfile button by its ID within the fragment_profile.xml layout
        editProfile = view.findViewById(R.id.EditProfile);

        // Set an OnClickListener to handle button clicks
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the fragment you want to navigate to
                EditProfile editProfileFragment = new EditProfile();

                // Get the FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.container, editProfileFragment);

                // Add the transaction to the back stack (optional)
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        // Find the Log Out button by its ID within the fragment_profile.xml layout
        logOutButton = view.findViewById(R.id.btnLogOut);
        imageProfilepic = view.findViewById(R.id.imageProfilepic);


        // Set an OnClickListener to handle button clicks
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the GetStartedScreen activity
                Intent intent = new Intent(requireContext(), GetStartedScreen.class);
                startActivity(intent);

            }
        });
        loadUserProfilePicture();
        setUserDetails(view);

        return view;
    }

    private void setUserDetails(View view) {
        // Retrieve the user's email from the arguments
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = preferences.getString("userName", "");
        Log.d("profile", "User Name: " + name);

        TextView nameTextView = view.findViewById(R.id.txtName);
        nameTextView.setText(name);
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
                    .into(imageProfilepic);
        } else {
            // If profilePic URL is empty, set a default image
            imageProfilepic.setImageResource(R.drawable.img_profilepic); // Replace with your default image resource
        }
    }
}


