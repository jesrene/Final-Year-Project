package com.jesrenesapplication.app;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class HomeFragment extends Fragment {

    private Interpreter tflite;
    private EditText heartRateInput;
    private Button predictButton;
    private TextView mentalStateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI elements
        heartRateInput = view.findViewById(R.id.heartRateTextView);
        predictButton = view.findViewById(R.id.button);
        mentalStateText = view.findViewById(R.id.textMentalState);

        // Load the TFLite model from the assets folder
        try {
            tflite = new Interpreter(loadModelFile(requireContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Retrieve the GoogleSignInAccount from the Intent
//        GoogleSignInAccount account = getActivity().getIntent().getParcelableExtra("googleSignInAccount");
//
//        ImageView imageProfilepic = view.findViewById(R.id.imageProfilepic);
//
//        if (account != null) {
//            // Get the user's profile picture Uri
//            Uri photoUri = account.getPhotoUrl();
//
//            if (photoUri != null) {
//                // Use Picasso to load and display the image
//                Log.d("PhotoUri", photoUri.toString());
//                Picasso.get().load(photoUri).into(imageProfilepic);
//            } else {
//                // If photoUri is null, set a default image
//                imageProfilepic.setImageResource(R.drawable.img_profilepic); // Replace with your default image resource
//            }
//        } else {
//            // If the GoogleSignInAccount is null, set a default image
//            imageProfilepic.setImageResource(R.drawable.img_profilepic); // Replace with your default image resource
//        }


        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float heartRate = Float.parseFloat(heartRateInput.getText().toString());

                    String prediction = predictStress(heartRate);
                    mentalStateText.setText(prediction);
                } catch (NumberFormatException e) {
                    mentalStateText.setText("Invalid input. Please enter a valid number.");
                }
            }
        });
    return view;
    }

    // Load the TFLite model from the assets folder
    private MappedByteBuffer loadModelFile(Context context) throws Exception {
        AssetFileDescriptor fileDescriptor;
        fileDescriptor = context.getAssets().openFd("knn_model_hr.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // Perform KNN prediction using the TFLite model
    private String predictStress(float heartRate) {
        // Create a ByteBuffer to hold the input data
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(4); // 4 bytes for a single float
        inputBuffer.order(ByteOrder.nativeOrder());

        // Copy the input data (float) into the ByteBuffer
        inputBuffer.putFloat(heartRate);

        // Prepare the output buffer
        float[][] output = new float[1][1];

        // Run inference
        tflite.run(inputBuffer, output);

        // The result is in the output[0]
        float prediction = output[0][0];

        // Perform some logic to convert the prediction to a string
        String predictionString = convertPredictionToString(prediction);

        return predictionString;
    }

    // Add your logic to convert the prediction value to a user-friendly string
    private String convertPredictionToString(float prediction) {
        if (prediction < 0.5) {
            return "Low Stress";
        } else {
            return "High Stress";
        }
    }
}

