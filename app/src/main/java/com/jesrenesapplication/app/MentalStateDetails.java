package com.jesrenesapplication.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MentalStateDetails extends Fragment {

    LinearLayout backButton;
    TextView mentalState;
    TextView about;
    TextView forYou;
    TextView mentalStateTextView; // assuming you have a TextView in your layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mental_state_stress, container, false);

        mentalState = view.findViewById(R.id.txtMentalState);
        about = view.findViewById(R.id.txtAbout);
        forYou = view.findViewById(R.id.txtForYou);

        backButton = view.findViewById(R.id.backToSummary);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the fragment you want to navigate to
                HomeFragment homeFragment = new HomeFragment();

                // Get the FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.container, homeFragment);

                // Add the transaction to the back stack (optional)
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                // Change the string in MentalStateDetails fragment
                updateMentalStateText();
            }
        });
        return view;
    }

    private void updateMentalStateText() {
        // Your logic to determine the string
        String newMentalState = "Your New Mental State";
        mentalStateTextView.setText(newMentalState);
    }
}
