package com.jesrenesapplication.app;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private LinearLayout mentalState;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView heartRateTextView;
    private OnDataPointListener heartRateListener;

    private GoogleApiClient mGoogleApiClient;

    private static final int REQUEST_BODY_SENSORS_PERMISSION = 1001;
    private static final int REQUEST_RESOLUTION = 1002; // Define this constant

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        mGoogleApiClient = new GoogleApiClient.Builder(requireContext())
                .addApi(Fitness.SENSORS_API)
                .useDefaultAccount()
                .addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                .addOnConnectionFailedListener(connectionResult -> {
                    // Handle connection failure
                    Log.e("GoogleFit", "Connection to Google Fit failed");
                    if (connectionResult.hasResolution()) {
                        try {
                            // Handle resolution, e.g., show a dialog to resolve the issue.
                            connectionResult.startResolutionForResult(requireActivity(), REQUEST_RESOLUTION);
                        } catch (IntentSender.SendIntentException e) {
                            // Handle the exception
                            Log.e("GoogleFit", "Exception when starting resolution: " + e.getMessage());
                        }
                    } else {
                        // Handle the failure, e.g., show an error message.
                        Log.e("GoogleFit", "Unresolvable failure");
                        Toast.makeText(requireContext(), "Failed to connect to Google Fit. Please check your permissions and network.", Toast.LENGTH_LONG).show();

                    }
                })

                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        // Handle successful connection
                        Log.d("GoogleFit", "Connected to Google Fit");
                    }
                    @Override
                    public void onConnectionSuspended(int i) {
                        // Handle connection suspension
                        Log.d("GoogleFit", "Connection to Google Fit suspended");
                    }
                })

                .build();

        mGoogleApiClient.connect();


        // Check if the app has the BODY_SENSORS permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
            // The permission is already granted; proceed with data retrieval
            Log.d("PermissionGranted", "BODY_SENSORS permission is granted.");

            // Check if a Google account has been logged in
            if (isGoogleAccountLoggedIn()) {
                Log.d("GoogleSignIn", "Google account is logged in.");
                accessHeartRateData();
            } else {
                Log.d("GoogleSignIn", "Google account is not logged in.");
                // You can handle the case where the Google account is not logged in, such as showing a login screen.
            }
        } else {
            // Request the BODY_SENSORS permission from the user
            requestBodySensorsPermission();
        }
    }

    private boolean isGoogleAccountLoggedIn() {
        // Get the Google account that is logged in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());

        if (account != null) {
            // A Google account is logged in
            String email = account.getEmail();

            // Log the Google email
            Log.d("GoogleSignIn", "Google account is logged in. Email: " + email);

            // You can also use 'account.getId()' to get the user's unique ID, or 'account.getDisplayName()' for their display name.

            return true;
        } else {
            // No Google account is logged in
            Log.d("GoogleSignIn", "No Google account is logged in.");
            return false;
        }
    }

    private void requestBodySensorsPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.BODY_SENSORS}, REQUEST_BODY_SENSORS_PERMISSION);
    }


    private void accessHeartRateData() {
        // Create an instance of the SensorsClient
        heartRateListener = dataPoint -> {
            for (Field field : dataPoint.getDataType().getFields()) {
                Value value = dataPoint.getValue(field);
                int heartRate = value.asInt();
                Log.i("HeartRateData", "Heart Rate: " + heartRate);

            }
        };
        // Subscribe to heart rate data recording
        Fitness.getRecordingClient(requireContext(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(requireContext())))
                .subscribe(DataType.TYPE_HEART_RATE_BPM)
                .addOnSuccessListener(aVoid -> {
                    Log.i("HeartRateSubscription", "Successfully subscribed to heart rate data recording!");
                    // Register the data listener for real-time updates
                    registerHeartRateDataListener();
                })
                .addOnFailureListener(e -> Log.e("HeartRateSubscription", "Failed to subscribe to heart rate data recording.", e));
    }
//
//        Fitness.getSensorsClient(requireContext(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(requireContext())))
//                .add(sensorRequest, heartRateListener)
//                .addOnSuccessListener(aVoid -> Log.d("SensorsClient", "Listener registered."))
//                .addOnFailureListener(e -> Log.e("SensorsClient", "Listener registration failed.", e));
//}

    private void registerHeartRateDataListener() {
        SensorRequest sensorRequest = new SensorRequest.Builder()
                .setDataType(DataType.TYPE_HEART_RATE_BPM)
                .setSamplingRate(1, TimeUnit.MINUTES)
                .build();

        Fitness.getSensorsClient(requireContext(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(requireContext())))
                .add(sensorRequest, heartRateListener)
                .addOnSuccessListener(aVoid -> Log.d("SensorsClient", "Listener registered."))
                .addOnFailureListener(e -> Log.e("SensorsClient", "Listener registration failed.", e));
    }


}
