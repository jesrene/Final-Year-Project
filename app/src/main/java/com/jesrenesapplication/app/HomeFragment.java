package com.jesrenesapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    LinearLayout mentalState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
        mentalState = view.findViewById(R.id.linearMentalstate);

        // Set an OnClickListener to handle button clicks
        mentalState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MentalStateDetails.class);
                startActivity(intent);
            }
        });

        return view;
    }
}


