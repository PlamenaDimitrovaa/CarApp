package com.example.carapp.Models;

import com.google.firebase.Timestamp;
import com.google.type.DateTime;

public class CarMaintenance {
    private String carId;
    private String maintenanceDate;
    private String type;
    private String description;
    private double cost;
    private boolean isCompleted;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public CarMaintenance() {
    }

    public CarMaintenance(String carId, String maintenanceDate, String type, String description, double cost, boolean isCompleted) {
        this.maintenanceDate = maintenanceDate;
        this.type = type;
        this.description = description;
        this.cost = cost;
        this.isCompleted = isCompleted;
        this.carId = carId;
    }

    public String getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
