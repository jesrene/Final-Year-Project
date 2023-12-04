package com.jesrenesapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HomeFragment extends Fragment {

    // Declare variables
    private EditText heartRateInput;
    private Button predictButton;
    private TextView mentalStateText;
    private Picasso picasso;

    // Method to set user details (name, profile picture)
    private void setUserDetails(View view) {
        // Retrieve the user's email and profile picture URL from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String profilePic = preferences.getString("userPhotoUrl", "");
        Log.d("EditProfileFragment", "pic: " + profilePic);

        String name = preferences.getString("userName", "");
        Log.d("profile", "User Name: " + name);

        // Set user's name in TextView
        TextView nameTextView = view.findViewById(R.id.txtName);
        nameTextView.setText(name);

        // Get the GoogleSignInAccount from the intent and display profile picture using Picasso
        GoogleSignInAccount account = getActivity().getIntent().getParcelableExtra("googleSignInAccount");

        ImageView imageProfilePicture = view.findViewById(R.id.imageProfilePicture);

        if (account != null) {
            // Get the user's profile picture Uri
            Uri photoUri = account.getPhotoUrl();
            if (picasso == null) {
                picasso = new Picasso.Builder(requireContext()).build();
            }
            if (photoUri != null) {
                picasso.load(photoUri)
                        .transform(new CropCircleTransformation()) // Apply circular transformation
                        .into(imageProfilePicture);
                Log.d("PhotoUri", photoUri.toString());
            } else {
                // If photoUri is null, set a default image
                imageProfilePicture.setImageResource(R.drawable.img_profilepic); // Replace with your default image resource
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen3_home, container, false);

        setUserDetails(view);
        // Initialize UI elements
        heartRateInput = view.findViewById(R.id.heartRateTextView);
        predictButton = view.findViewById(R.id.button);
        mentalStateText = view.findViewById(R.id.textMentalState);
        LinearLayout mentalStateLayout = view.findViewById(R.id.mentalStateDetails);

        // Set OnClickListener to navigate to MentalStateDetails fragment
        mentalStateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prepare data to pass to MentalStateDetails fragment
                MentalStateDetails mentalStateDetails = new MentalStateDetails();
                Bundle args = new Bundle();
                args.putBoolean("isStressed", mentalStateText.getText().toString().equals("In Stress"));

                // Replace current fragment with MentalStateDetails
                mentalStateDetails.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, mentalStateDetails);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Set OnClickListener for predictButton
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Prepare URL for prediction based on heart rate input
                String url = "https://jesrenecheoy.pythonanywhere.com/?hr=" + heartRateInput.getText();
                RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

                // Create a JsonObjectRequest to get a prediction
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            // Handle the response and update UI with the prediction result
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("response", response.getString("prediction"));
                                    int prediction = response.getInt("prediction");

                                    if (prediction == 0) {
                                        mentalStateText.setText("Non-stress");
                                        mentalStateText.setTextColor(Color.BLACK);

                                    } else if (prediction == 1) {
                                        mentalStateText.setText("In Stress");
                                        mentalStateText.setTextColor(Color.RED);
                                    } else {
                                        mentalStateText.setText("Unknown prediction: " + prediction);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                         // Error handling
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("response", ("received the error"));
                                mentalStateText.setText("ERROR. Enter integers.");
                                mentalStateText.setTextColor(Color.RED);
                            }
                        });

                requestQueue.add(jsonObjectRequest);
            }
        });
        return view;
    }
}

