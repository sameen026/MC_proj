package com.example.myapplication.Model;

public class Plaza {
    String plazaId;
    String plazaName;
    int totalSlots;
    int availableSlots;
    double carFee;
    double bikeFee;
    String carPolicyType;
    String bikePolicyType;
    String status;
    double plazaLatitude;
    double plazaLongitude;
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    public Plaza(String plazaId, String plazaName, int totalSlots, int availableSlots, double carFee, double bikeFee, String carPolicyType, String bikePolicyType, String status, double plazaLatitude, double plazaLongitude, String userId) {
        this.plazaId = plazaId;
        this.plazaName = plazaName;
        this.totalSlots = totalSlots;
        this.availableSlots = availableSlots;
        this.carFee = carFee;
        this.bikeFee = bikeFee;
        this.carPolicyType = carPolicyType;
        this.bikePolicyType = bikePolicyType;
        this.status = status;
        this.plazaLatitude = plazaLatitude;
        this.plazaLongitude = plazaLongitude;
        this.userId = userId;
    }

    public String getId() {
        return plazaId;
    }

    public void setId(String id) {
        this.plazaId = id;
    }

    public String getPlazaName() {
        return plazaName;
    }

    public void setPlazaName(String plazaName) {
        this.plazaName = plazaName;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public double getCarFee() {
        return carFee;
    }

    public void setCarFee(double carFee) {
        this.carFee = carFee;
    }

    public double getBikeFee() {
        return bikeFee;
    }

    public void setBikeFee(double bikeFee) {
        this.bikeFee = bikeFee;
    }

    public String getCarPolicyType() {
        return carPolicyType;
    }

    public void setCarPolicyType(String carPolicyType) {
        this.carPolicyType = carPolicyType;
    }

    public String getBikePolicyType() {
        return bikePolicyType;
    }

    public void setBikePolicyType(String bikePolicyType) {
        this.bikePolicyType = bikePolicyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPlazaLatitude() {
        return plazaLatitude;
    }

    public void setPlazaLatitude(double plazaLatitude) {
        this.plazaLatitude = plazaLatitude;
    }

    public double getPlazaLongitude() {
        return plazaLongitude;
    }

    public void setPlazaLongitude(double plazaLongitude) {
        this.plazaLongitude = plazaLongitude;
    }
}
