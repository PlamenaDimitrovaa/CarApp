package com.example.carapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.carapp.Activity.AddCarActivity;
import com.example.carapp.Activity.CarListActivity;
import com.example.carapp.Models.CarMaintenance;
import com.example.carapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMaintenanceDialogFragment extends DialogFragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =
            db.collection("CarMaintenance");
    private EditText dateInput, typeInput, descriptionInput, costInput, completedInput;
    private String carId;

    public static AddMaintenanceDialogFragment newInstance(String carId) {
        AddMaintenanceDialogFragment fragment = new AddMaintenanceDialogFragment();
        Bundle args = new Bundle();
        args.putString("carId", carId);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        carId = getArguments().getString("carId");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate and set the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_maintenance, null);

        dateInput = view.findViewById(R.id.editTextMaintenanceDate);
        typeInput = view.findViewById(R.id.editTextMaintenanceType);
        descriptionInput = view.findViewById(R.id.editTextMaintenanceDescription);
        costInput = view.findViewById(R.id.editTextMaintenanceCost);
        completedInput = view.findViewById(R.id.editTextMaintenanceCost);

        Button saveMaintenance = (Button) view.findViewById(R.id.saveMaintenanceButton);

        saveMaintenance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    saveMaintenance();
                } catch (ParseException e) {
                }
            }
        });

        builder.setView(view)
                .setTitle("Добавяне на поддръжка")
                .setPositiveButton("Запази", (dialog, id) -> {
                    try {
                        saveMaintenance();
                    } catch (ParseException e) {
                    }
                })
                .setNegativeButton("Затвори", (dialog, id) -> dialog.cancel());

        return builder.create();
    }

    private void saveMaintenance() throws ParseException {
        String date = dateInput.getText().toString();
        String type = typeInput.getText().toString();
        String description = descriptionInput.getText().toString();
        double cost = Double.parseDouble(costInput.getText().toString());
        boolean isCompleted = Boolean.parseBoolean(completedInput.getText().toString());

        CarMaintenance maintenance = new CarMaintenance(carId, date, type, description, cost, isCompleted);

        collectionReference.add(maintenance).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Поддръжката е добавена успешно!", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Проблем с добавянето на поддръжката!", Toast.LENGTH_SHORT).show();
            }
        });;
    }
}
