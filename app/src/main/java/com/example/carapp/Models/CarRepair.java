package com.example.carapp.Models;

import com.google.firebase.Timestamp;
import com.google.type.DateTime;

public class CarRepair {
    private String dateOfRepair;
    private Double cost;
    private String description;
    private String carId;

    public CarRepair() {
    }

    public String getDateOfRepair() {
        return dateOfRepair;
    }

    public void setDateOfRepair(String dateOfRepair) {
        this.dateOfRepair = dateOfRepair;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public CarRepair(String carId, String dateOfRepair, Double cost, String description) {
        this.dateOfRepair = dateOfRepair;
        this.cost = cost;
        this.description = description;
        this.carId = carId;
    }
}
