package com.example.carapp.Models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Car {
    private String id;
    private String model;
    private int doors;
    private int horsePower;
    private String make;
    private double fuelConsumption;
    private String userId;
    private Timestamp timeAdded;
    private String userName;
    private String imageUrl;
    private int year;

    public Car() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Car(String id, String model, int doors, int horsePower, String make, double fuelConsumption, String userId, Timestamp timeAdded, String userName, String imageUrl, int year) {
        this.id = id;
        this.model = model;
        this.doors = doors;
        this.horsePower = horsePower;
        this.make = make;
        this.fuelConsumption = fuelConsumption;
        this.userId = userId;
        this.timeAdded = timeAdded;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.year = year;
    }
}




