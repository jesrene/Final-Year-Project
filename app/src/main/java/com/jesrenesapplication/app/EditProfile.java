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

    // Declaring variables
    LinearLayout backButton;
    ImageView imageProfilePicture;
    private Picasso picasso;

    // Method to set user details in the UI
    private void setUserDetails(View view) {
        // Retrieve the user's email from the arguments
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String email = getArguments() != null ? getArguments().getString("userEmail") : "";

        email = preferences.getString("userEmail", "");
        Log.d("EditProfileFragment", "User Email: " + email);

        // String name = getArguments() != null ? getArguments().getString("userName") : "";
        String name = preferences.getString("userName", "");
        Log.d("EditProfileFragment", "User Name: " + name);

        // Setting retrieved email and name in corresponding TextViews
        TextView emailTextView = view.findViewById(R.id.txtEmail);
        emailTextView.setText(email);

        TextView nameTextView = view.findViewById(R.id.txtName);
        nameTextView.setText(name);
    }

    // Method executed when creating the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Setting user details in the UI
        View view = inflater.inflate(R.layout.screen9_edit_profile, container, false);
        setUserDetails(view);

        // Initializing views and setting onClickListener for back button
        backButton = view.findViewById(R.id.backToProfile);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigating back to the ProfileFragment
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, profileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Loading and displaying user profile picture
        loadUserProfilePicture();
        return view;
    }

    // Load user's profile picture
    private void loadUserProfilePicture() {
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
            imageProfilePicture.setImageResource(R.drawable.img_profilepic);
        }
    }
}

