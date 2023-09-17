package com.jesrenesapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class RewardsFragment extends Fragment {
    Button playGame;
    Button viewVoucher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);


        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
        playGame = view.findViewById(R.id.btnPlayGame);

        // Set an OnClickListener to handle button clicks
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizScreen.class);
                startActivity(intent);
            }
        });

        // Find the btnPlayGame button by its ID within the fragment_rewards.xml layout
        viewVoucher = view.findViewById(R.id.btnViewVoucher);

        // Set an OnClickListener to handle button clicks
        viewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VoucherScreen.class);
                startActivity(intent);
            }
        });

        return view;
    }
}