package com.example.myapplication.Model;

import java.sql.Time;

public class ParkedVehicle {
    String vehicleId;
    Time entranceTime;
    Time exitTime;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Time getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Time entranceTime) {
        this.entranceTime = entranceTime;
    }

    public Time getExitTime() {
        return exitTime;
    }

    public void setExitTime(Time exitTime) {
        this.exitTime = exitTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    public ParkedVehicle(String vehicleId, Time entranceTime, Time exitTime, String vehicleNumber, String plazaId) {
        this.vehicleId = vehicleId;
        this.entranceTime = entranceTime;
        this.exitTime = exitTime;
        this.vehicleNumber = vehicleNumber;
        this.plazaId = plazaId;
    }

    String vehicleNumber;
    String plazaId;
}
