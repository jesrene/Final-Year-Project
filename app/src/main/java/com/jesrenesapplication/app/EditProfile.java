package com.jesrenesapplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EditProfile extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 1;

    LinearLayout backButton;
    FrameLayout uploadPhoto;

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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setUserDetails(view);

        backButton = view.findViewById(R.id.backToProfile);

        uploadPhoto = view.findViewById(R.id.frameUploadphoto);

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

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            // Now, you can use the selectedImageUri to work with the selected image.
            // You might want to display it in an ImageView or perform further processing.
        }
    }
}
