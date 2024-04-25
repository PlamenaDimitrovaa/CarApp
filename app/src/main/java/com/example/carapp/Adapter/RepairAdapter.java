package com.example.carapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carapp.Models.CarRepair;
import com.example.carapp.R;

import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> {
    private List<CarRepair> repairList;

    public RepairAdapter(List<CarRepair> repairList) {
        this.repairList = repairList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_repair, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarRepair repair = repairList.get(position);
        holder.dateOfRepair.setText(String.format("Дата: ", repair.getDateOfRepair().toString()));
        holder.cost.setText(String.format("Цена: $%.2f", repair.getCost()));
        holder.description.setText(repair.getDescription());
    }

    @Override
    public int getItemCount() {
        return repairList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateOfRepair, cost, description;

        public ViewHolder(View itemView) {
            super(itemView);
            dateOfRepair = itemView.findViewById(R.id.dateOfRepair);
            cost = itemView.findViewById(R.id.cost);
            description = itemView.findViewById(R.id.description);
        }
    }
}
