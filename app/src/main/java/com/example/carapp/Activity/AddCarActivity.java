package com.example.carapp.Activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carapp.Models.Car;
import com.example.carapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class AddCarActivity extends AppCompatActivity {

    // Widgets
    private Button saveButton;
    private ImageView addPhotoBtn;
    private ProgressBar progressBar;
    private EditText modelEditText;
    private EditText makeEditText;

    private EditText doorsEditText;

    private EditText horsePowerEditText;

    private EditText fuelConsumptionEditText;

    private EditText yearEditText;
    private ImageView imageView;

    // Firebase (FireStore)
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =
            db.collection("Car");

    // Firebase (Storage)
    private StorageReference storageReference;


    // Firebase Auth : UserId and UserName
    private String currentUserId;
    private String currentUserName;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Using Activity Result Launcher
    ActivityResultLauncher<String> mTakePhoto;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        progressBar = findViewById(R.id.post_progressBar);
        modelEditText = findViewById(R.id.post_description_et);
        makeEditText = findViewById(R.id.post_title_et);
        doorsEditText = findViewById(R.id.post_doors_et);
        horsePowerEditText = findViewById(R.id.post_horsepower_et);
        fuelConsumptionEditText = findViewById(R.id.post_fuelconsumption_et);
        yearEditText = findViewById(R.id.post_year_et);
        imageView = findViewById(R.id.post_imageView);
        saveButton = findViewById(R.id.post_save_car_button);
        addPhotoBtn = findViewById(R.id.postCameraButton);

        progressBar.setVisibility(View.INVISIBLE);

        // Firebase Storage Reference
        storageReference = FirebaseStorage.getInstance()
                .getReference();

        // Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Getting the Current User
        if (user != null){
            currentUserId = user.getUid();
            currentUserName = user.getDisplayName();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCar();
            }
        });

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        // Showing the image
                        imageView.setImageURI(result);

                        // Get the image URI
                        imageUri = result;
                    }
                }
        );

        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting Image From The Gallery
                mTakePhoto.launch("image/*");
            }
        });
    }


    private void SaveCar() {

        String make = makeEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        String doorsAsString = doorsEditText.getText().toString().trim();
        String horsePowerAsString = horsePowerEditText.getText().toString().trim();
        String yearAsString = yearEditText.getText().toString().trim();
        String fuelConsumptionAsString = fuelConsumptionEditText.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(model) && !TextUtils.isEmpty(make) && !TextUtils.isEmpty(doorsAsString)
                && !TextUtils.isEmpty(horsePowerAsString) && !TextUtils.isEmpty(fuelConsumptionAsString) && !TextUtils.isEmpty(yearAsString)
                && imageUri !=null){

            // the saving path of the images in Firebase Storage:
            // ........./car_images/my_image_202310071430.png
            final StorageReference filePath = storageReference.
                    child("car_images")
                    .child("my_image_"+ Timestamp.now().getSeconds());

            // Uploading the image
            filePath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    Integer doors = Integer.parseInt(doorsAsString);
                                    Integer horsePower = Integer.parseInt(horsePowerAsString);
                                    Integer year = Integer.parseInt(yearAsString);
                                    Double fuelConsumption = Double.parseDouble(fuelConsumptionAsString);
                                    // Creating a Car Object
                                    Car car = new Car();
                                    car.setMake(make);
                                    car.setModel(model);
                                    car.setImageUrl(imageUrl);
                                    car.setDoors(doors);
                                    car.setHorsePower(horsePower);
                                    car.setYear(year);
                                    car.setFuelConsumption(fuelConsumption);
                                    car.setUserId(user.getUid());
                                    car.setUserName(user.getEmail());
                                    car.setId(java.util.UUID.randomUUID().toString());

                                    car.setTimeAdded(new Timestamp(new Date()));


                                    collectionReference.add(car)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    progressBar.setVisibility(View.INVISIBLE);

                                                    Intent i = new Intent(AddCarActivity.this, CarListActivity.class);

                                                    startActivity(i);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AddCarActivity.this,
                                                            "Failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AddCarActivity.this,
                                    "Failed !!!!", Toast.LENGTH_SHORT).show();
                        }
                    });


        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
    }
}