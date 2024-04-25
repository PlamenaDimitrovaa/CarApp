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

import com.example.carapp.Activity.CarDetailsActivity;
import com.example.carapp.Models.CarRepair;
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

public class AddRepairDialogFragment extends DialogFragment {

    private EditText dateOfRepairInput, costInput, descriptionInput;
    private String carId;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference =
            db.collection("CarRepair");

    public static AddRepairDialogFragment newInstance(String carId) {
        AddRepairDialogFragment fragment = new AddRepairDialogFragment();
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
        View view = inflater.inflate(R.layout.dialog_add_repair, null);

        dateOfRepairInput = view.findViewById(R.id.editTextDateOfRepair);
        costInput = view.findViewById(R.id.editTextRepairCost);
        descriptionInput = view.findViewById(R.id.editTextRepairDescription);

        Button saveRepair = (Button) view.findViewById(R.id.saveButton);

        saveRepair.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    saveRepair();
                } catch (ParseException e) {
                }
            }
        });

        builder.setView(view)
                .setTitle("Добави ремонт")
                .setPositiveButton("Запази", (dialog, id) -> {
                    try {
                        saveRepair();
                    } catch (ParseException e) {
                    }
                })
                .setNegativeButton("Откажи", (dialog, id) -> dialog.cancel());

        return builder.create();
    }

    private void saveRepair() throws ParseException {
        String dateOfRepair = dateOfRepairInput.getText().toString();
        double cost = Double.parseDouble(costInput.getText().toString());
        String description = descriptionInput.getText().toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Car").child(carId).child("repairs");
        String repairId = databaseReference.push().getKey();  // Generate a unique key for the repair

        CarRepair repair = new CarRepair(carId, dateOfRepair, cost, description);

        collectionReference.add(repair).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Ремонтът е добавен успешно", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Възникна проблем с добавянето на ремонта", Toast.LENGTH_SHORT).show();
            }
        });;
    }
}

