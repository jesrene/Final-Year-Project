package com.jesrenesapplication.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;

import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.tasks.Task;
import com.jesrenesapplication.app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {

    private LinearLayout mentalState;
    private GoogleApiClient mGoogleApiClient;
    private TextView heartRateTextView;
    private TextView edaTextView;
    private OnDataPointListener heartRateListener;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
//        // Add your GoogleApiClient setup here
//        mGoogleApiClient = new GoogleApiClient.Builder(requireContext())
//                .addApi(Fitness.SENSORS_API)
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(Bundle bundle) {
//                        // Connected successfully
//                        DataSourcesRequest request = new DataSourcesRequest.Builder()
//                                .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
//                                .setDataSourceTypes(DataSource.TYPE_RAW)
//                                .build();
//
//                        ResultCallback<DataSourcesResult> sourcesResultCallback = new ResultCallback<DataSourcesResult>() {
//                            @Override
//                            public void onResult(DataSourcesResult dataSourcesResult) {
//                                for (DataSource dataSource : dataSourcesResult.getDataSources()) {
//                                    if (DataType.TYPE_HEART_RATE_BPM.equals(dataSource.getDataType())) {
//                                        registerHeartRateListener(dataSource, DataType.TYPE_HEART_RATE_BPM);
//                                    }
//                                }
//                            }
//                        };
//
//                        Fitness.SensorsApi.findDataSources(mGoogleApiClient, request)
//                                .setResultCallback(sourcesResultCallback);
//                    }
//
//                    @Override
//                    public void onConnectionSuspended(int i) {
//                        // Connection suspended
//                    }
//                })
//                .build();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mentalState = view.findViewById(R.id.linearMentalstate);

//        mentalState.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkForPermission();
//            }
//        });

        return view;
    }

//    private void checkForPermission() {
//        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestActivityRecognitionPermission();
//        } else {
//            displayHeartRateAndEDA();
//        }
//    }
//
//    private void displayHeartRateAndEDA() {
//        // Your code to retrieve and display heart rate and EDA data should go here.
//        // This code will depend on how you've set up data sources and listeners.
//
//        // Example: Update UI elements with heart rate and EDA data
//        // heartRateTextView.setText("Heart Rate: " + heartRateValue);
//        // edaTextView.setText("EDA: " + edaValue);
//    }
//
//    private void requestActivityRecognitionPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION)) {
//            Toast.makeText(requireContext(), "Activity recognition permission is required to monitor your activity.", Toast.LENGTH_LONG).show();
//        }
//
//        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
//    }

    // Handle the result of the permission request.
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_ACTIVITY_RECOGNITION_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, you can proceed with your desired action.
//            } else {
//                // Permission denied; handle this case as needed.
//                Toast.makeText(requireContext(), "Permission denied. You may not be able to monitor your activity.", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    // Register a listener for heart rate data
//    private void registerHeartRateListener(DataSource dataSource, DataType dataType) {
//        heartRateListener = new OnDataPointListener() {
//            @Override
//            public void onDataPoint(DataPoint dataPoint) {
//                for (Field field : dataPoint.getDataType().getFields()) {
//                    Value value = dataPoint.getValue(field);
//                    float heartRate = value.asFloat();
//                    updateHeartRateUI(heartRate);
//                }
//            }
//        };
//
//        SensorRequest request = new SensorRequest.Builder()
//                .setDataSource(dataSource)
//                .setDataType(dataType)
//                .setSamplingRate(3, TimeUnit.SECONDS)
//                .build();
//    }
//
//    // Update the UI with heart rate data
//    private void updateHeartRateUI(float heartRate) {
//        heartRateTextView.setText("Heart Rate: " + heartRate);
//    }
}
