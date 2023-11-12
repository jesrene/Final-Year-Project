package com.jesrenesapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
    private EditText heartRateInput;
    private Button predictButton;
    private TextView mentalStateText;
    private Picasso picasso;

    private void setUserDetails(View view) {
        // Retrieve the user's email and profile picture URL from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String profilePic = preferences.getString("userPhotoUrl", "");
        Log.d("EditProfileFragment", "pic: " + profilePic);

        String name = preferences.getString("userName", "");
        Log.d("profile", "User Name: " + name);

        TextView nameTextView = view.findViewById(R.id.txtName);
        nameTextView.setText(name);

        // Retrieve the GoogleSignInAccount from the intent
        GoogleSignInAccount account = getActivity().getIntent().getParcelableExtra("googleSignInAccount");

        ImageView imageProfilePicture = view.findViewById(R.id.imageProfilePicture);

        if (account != null) {
            // Get the user's profile picture Uri
            Uri photoUri = account.getPhotoUrl();

            // Initialize Picasso (if not already initialized)
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
        } else {
            // Handle the case where GoogleSignInAccount is null
            // You may want to display an error or take appropriate action.
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setUserDetails(view);
        // Initialize UI elements
        heartRateInput = view.findViewById(R.id.heartRateTextView);
        predictButton = view.findViewById(R.id.button);
        mentalStateText = view.findViewById(R.id.textMentalState);

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://jesrenecheoy.pythonanywhere.com/?hr=" + heartRateInput.getText(); //todo
                RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("response", response.getString("prediction"));
                                    int prediction = response.getInt("prediction");

                                    if (prediction == 0) {
                                        mentalStateText.setText("Non-stress");
                                    } else if (prediction == 1) {
                                        mentalStateText.setText("In Stress");
                                    } else {
                                        // Handle other prediction values if needed
                                        mentalStateText.setText("Unknown prediction: " + prediction);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("response", ("received the error"));
                                mentalStateText.setText("that didnt work out, check problem");
                            }
                        });
                // Add the request to the RequestQueue
                // Assuming you have a RequestQueue object named 'requestQueue'
                requestQueue.add(jsonObjectRequest);
            }
        });

        return view;
    }
}

