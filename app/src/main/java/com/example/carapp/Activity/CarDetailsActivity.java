package com.example.carapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.carapp.Fragments.AddMaintenanceDialogFragment;
import com.example.carapp.Fragments.AddRepairDialogFragment;
import com.example.carapp.Adapter.MaintenanceAdapter;
import com.example.carapp.Adapter.RepairAdapter;
import com.example.carapp.Models.Car;
import com.example.carapp.Models.CarMaintenance;
import com.example.carapp.Models.CarRepair;
import com.example.carapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CarDetailsActivity extends AppCompatActivity {
    private String carId;
    private ImageView carImage;
    private TextView carModel, carMakeYear, carDetails;
    private RecyclerView repairsRecyclerView, maintenanceRecyclerView;
    private RepairAdapter repairAdapter;
    private MaintenanceAdapter maintenanceAdapter;
    private List<CarRepair> repairList = new ArrayList<>();
    private List<CarMaintenance> maintenanceList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =
            db.collection("Car");

    private CollectionReference maintenanceCollectionReference =
            db.collection("CarMaintenance");

    private CollectionReference carRepairCollectionReference =
            db.collection("CarRepair");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carImage = findViewById(R.id.carImage);
        carModel = findViewById(R.id.carModel);
        carMakeYear = findViewById(R.id.carMakeYear);
        carDetails = findViewById(R.id.carDetails);
        repairsRecyclerView = findViewById(R.id.repairsRecyclerView);
        maintenanceRecyclerView = findViewById(R.id.maintenanceRecyclerView);
        maintenanceList = new ArrayList<>();
        repairList = new ArrayList<>();

        carId = getIntent().getStringExtra("carDetails");

        repairsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        maintenanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repairAdapter = new RepairAdapter(repairList);
        maintenanceAdapter = new MaintenanceAdapter(maintenanceList);
        repairsRecyclerView.setAdapter(repairAdapter);
        maintenanceRecyclerView.setAdapter(maintenanceAdapter);

        // Load car details
        if (carId != null) {
            loadCarDetails(carId);
        }
    }

    private void loadCarDetails(String carId) {
        maintenanceCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot maintenances: queryDocumentSnapshots)
                {
                    CarMaintenance maintenance = maintenances.toObject(CarMaintenance.class);

                    if (maintenance.getCarId().equals(carId)) {
                        maintenanceList.add(maintenance);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CarDetailsActivity.this,
                        "Opps! Something went wrong!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        carRepairCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot repairs: queryDocumentSnapshots)
                {
                    CarRepair repair = repairs.toObject(CarRepair.class);

                    if (repair.getCarId().equals(carId)) {
                        repairList.add(repair);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CarDetailsActivity.this,
                        "Opps! Something went wrong!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot cars: queryDocumentSnapshots)
                {
                    Car car = cars.toObject(Car.class);

                    if (car.getId().equals(carId)) {
                        updateUI(car);
                        break;
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CarDetailsActivity.this,
                        "Opps! Something went wrong!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAddRepairDialog(View v) {
        AddRepairDialogFragment dialog = AddRepairDialogFragment.newInstance(carId);
        dialog.show(getSupportFragmentManager(), "AddRepair");
    }

    public void showAddMaintenanceDialog(View v) {
        // Open a dialog or new activity to add maintenance
        AddMaintenanceDialogFragment dialog = AddMaintenanceDialogFragment.newInstance(carId);
        dialog.show(getSupportFragmentManager(), "AddMaintenance");
        maintenanceList.clear();
    }

    private void updateUI(Car car) {
        // Load image using Picasso or Glide
        Glide.with(this).load(car.getImageUrl()).into(carImage);

        // Set text details
        carModel.setText(car.getModel());
        carMakeYear.setText(String.format("%s, %d", car.getMake(), car.getYear()));
        carDetails.setText(String.format("Врати: %d\nКонски сили: %d\nРазход на гориво: %.2f Л/100км",
                car.getDoors(), car.getHorsePower(), car.getFuelConsumption()));

        repairAdapter.notifyDataSetChanged();
        maintenanceAdapter.notifyDataSetChanged();
    }
}