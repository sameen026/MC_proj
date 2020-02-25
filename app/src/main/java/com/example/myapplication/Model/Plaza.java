package com.example.myapplication.Model;


import java.io.Serializable;

public class Plaza  implements Serializable {
    String plazaID;
    String plazaName;
    int bikeAvailableSlots;
    int carAvailableSlots;
    int bikeTotalSlots;
    int carTotalSlots;
    double carFee;
    double bikeFee;
    String carPolicyType;
    String bikePolicyType;
    String status;
    double plazaLatitude;
    double plazaLongitude;
    String userId;
    String area;

    public String getPlazaID() {
        return plazaID;
    }

    public void setPlazaID(String plazaID) {
        this.plazaID = plazaID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getBikeAvailableSlots() {
        return bikeAvailableSlots;
    }

    public void setBikeAvailableSlots(int bikeAvailableSlots) {
        this.bikeAvailableSlots = bikeAvailableSlots;
    }

    public int getCarAvailableSlots() {
        return carAvailableSlots;
    }

    public void setCarAvailableSlots(int carAvailableSlots) {
        this.carAvailableSlots = carAvailableSlots;
    }

    public int getBikeTotalSlots() {
        return bikeTotalSlots;
    }

    public void setBikeTotalSlots(int bikeTotalSlots) {
        this.bikeTotalSlots = bikeTotalSlots;
    }

    public int getCarTotalSlots() {
        return carTotalSlots;
    }

    public void setCarTotalSlots(int carTotalSlots) {
        this.carTotalSlots = carTotalSlots;
    }

    public Plaza(String plazaID, String plazaName, int bikeAvailableSlots, int carAvailableSlots, int bikeTotalSlots, int carTotalSlots, double carFee, double bikeFee, String carPolicyType, String bikePolicyType, String status, double plazaLatitude, double plazaLongitude, String userId, String area) {
        this.plazaID = plazaID;
        this.plazaName = plazaName;
        this.bikeAvailableSlots = bikeAvailableSlots;
        this.carAvailableSlots = carAvailableSlots;
        this.bikeTotalSlots = bikeTotalSlots;
        this.carTotalSlots = carTotalSlots;
        this.carFee = carFee;
        this.bikeFee = bikeFee;
        this.carPolicyType = carPolicyType;
        this.bikePolicyType = bikePolicyType;
        this.status = status;
        this.plazaLatitude = plazaLatitude;
        this.plazaLongitude = plazaLongitude;
        this.userId = userId;
        this.area = area;
    }

    public Plaza() {
    }

    public String getUserId() {
        return userId;
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }




    public String getPlazaName() {
        return plazaName;
    }

    public void setPlazaName(String plazaName) {
        this.plazaName = plazaName;
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
