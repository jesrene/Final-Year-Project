package com.jesrenesapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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

public class MentalStateDetails extends Fragment {

    LinearLayout backButton;
    TextView mentalState;
    TextView about;
    TextView forYou;
    private Picasso picasso;
    ImageView imageProfilePicture;

    TextView mentalStateTextView; // assuming you have a TextView in your layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen4_mental_state_details, container, false);

        mentalState = view.findViewById(R.id.txtMentalState);
        about = view.findViewById(R.id.txtAbout);
        forYou = view.findViewById(R.id.txtForYou);
        imageProfilePicture = view.findViewById(R.id.imageProfilePicture);


        // Retrieve stress information from arguments
        boolean isStressed = getArguments().getBoolean("isStressed", false);

        if (isStressed) {
            // Update UI for stressed state
            mentalState.setText("Stressed");
            about.setText("Stress is a normal human reaction that happens to everyone. In fact, the human body is designed to experience stress and react to it. When you experience changes or challenges (stressors), your body produces physical and mental responses.");
            forYou.setText(Html.fromHtml("You can’t avoid stress, but you can stop it from becoming overwhelming by practicing some daily strategies: <br><br>" +
                    "<b>Regular Exercise:</b> Physical activity is a powerful stress reliever. " +
                    "When you feel stress creeping in, taking a break for a short walk, jog, or even " +
                    "a few minutes of stretching can help release endorphins, which are natural mood lifters. " +
                    "Regular exercise can also improve your overall resilience to stress. <br><br>" +
                    "<b>Daily Reflection:</b> At the end of each day, take a moment to reflect on your accomplishments " +
                    "rather than dwelling on what you didn't get done. This practice of gratitude and positive thinking " +
                    "can shift your focus away from stressors and help you appreciate your achievements, no matter how small they may seem. " +
                    "<br><br>" +
                    "<b>Social Connections:</b> Spend time with friends and loved ones who provide emotional support and understanding. " +
                    "Social connections can buffer the effects of stress and improve your mood. <br><br>" +
                    "Remember that stress is a common part of life, but it's essential to address it proactively. " +
                    "By incorporating these strategies into your daily routine, you can better manage stress and maintain a healthier, " +
                    "more balanced life. If stress becomes overwhelming or chronic, don't hesitate to seek professional help " +
                    "to develop more targeted coping strategies."));
        }
        else {
        // Update UI for non-stressed state
        mentalState.setText("Non-stress");
        about.setText("Experiencing a sense of calm and tranquility is a wonderful state of being. When you are not under stress, your mind and body can function optimally, allowing you to embrace life fully.");
        forYou.setText(Html.fromHtml("While you are in a state of non-stress, it is a perfect time to enhance your well-being with these daily strategies:" +
                "<br><br>" +
                "<b>Connect with Positivity:</b> Surround yourself with positive influences. Engage with activities and people who uplift your spirits " +
                "and contribute to a positive environment. Positive connections can be a powerful antidote to stress. <br><br>" +
                "<b>Healthy Routines:</b> Establishing healthy routines can further promote a stress-free lifestyle. Ensure you get adequate sleep, " +
                "maintain a balanced diet, and take breaks to rejuvenate throughout the day. A well-nourished body supports a calm and focused mind. " +
                "<br><br>" +
                "Remember, enjoying a non-stressful life is a journey, and it's perfectly okay to savor these moments. " +
                "By incorporating these suggestions into your routine, you can cultivate a lifestyle that fosters peace and fulfillment. " +
                "If you ever feel the need for additional support or guidance, seeking advice from trusted friends or professionals is always a positive step forward."));
    }

        backButton = view.findViewById(R.id.backToSummary);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, homeFragment);
                transaction.addToBackStack(null);
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
