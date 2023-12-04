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
    ImageView imageProfilePicture;
    private Picasso picasso;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen8_profile, container, false);

        // Find and set OnClickListener for the EditProfile button
        editProfile = view.findViewById(R.id.EditProfile);

        // Set an OnClickListener to handle button clicks
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an instance of the EditProfile fragment
                EditProfile editProfileFragment = new EditProfile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, editProfileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Find and set OnClickListener for the Log Out button
        logOutButton = view.findViewById(R.id.btnLogOut);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the GetStartedScreen activity
                Intent intent = new Intent(requireContext(), GetStartedScreen.class);
                startActivity(intent);

            }
        });

        // Load user profile picture and details
        loadUserProfilePicture();
        setUserDetails(view);

        return view;
    }

    // Method to set user details (in this case, user's name)
    private void setUserDetails(View view) {
        // Retrieve the user's name from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = preferences.getString("userName", "");
        Log.d("profile", "User Name: " + name);

        // Set the user's name in the TextView
        TextView nameTextView = view.findViewById(R.id.txtName);
        nameTextView.setText(name);
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


