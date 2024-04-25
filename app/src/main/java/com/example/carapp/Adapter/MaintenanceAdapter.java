package com.example.carapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carapp.Models.CarMaintenance;
import com.example.carapp.R;

import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.ViewHolder> {
    private List<CarMaintenance> maintenanceList;

    public MaintenanceAdapter(List<CarMaintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_maintenance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView maintenanceDate, type, description, cost, isCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            maintenanceDate = itemView.findViewById(R.id.maintenanceDate);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
            isCompleted = itemView.findViewById(R.id.isCompleted);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarMaintenance maintenance = maintenanceList.get(position);
        holder.maintenanceDate.setText(String.format("Дата на поддръжка: %s", maintenance.getMaintenanceDate()));
        holder.type.setText(String.format("Тип: %s", maintenance.getType()));
        holder.description.setText(String.format("Описание: %s", maintenance.getDescription()));
        holder.cost.setText(String.format("Цена: $%.2f", maintenance.getCost()));
        holder.isCompleted.setText(String.format("Завършено: %s", maintenance.isCompleted() ? "Yes" : "No"));
    }
}