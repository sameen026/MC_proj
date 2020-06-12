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
    String requestStatus;
    boolean isApprovedByAdmin;
    double plazaLatitude;
    double plazaLongitude;
    String userId;
    String area;

    public Plaza(){

    }

    public Plaza(String plazaID, String plazaName, int bikeAvailableSlots, int carAvailableSlots, int bikeTotalSlots, int carTotalSlots, int carFee, int bikeFee, String carPolicyType, String bikePolicyType, String status, String requestStatus, boolean isApprovedByAdmin, double plazaLatitude, double plazaLongitude, String userId, String area) {
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
        this.requestStatus = requestStatus;
        this.isApprovedByAdmin = isApprovedByAdmin;
        this.plazaLatitude = plazaLatitude;
        this.plazaLongitude = plazaLongitude;
        this.userId = userId;
        this.area = area;
    }

    public String getPlazaID() {
        return plazaID;
    }

    public void setPlazaID(String plazaID) {
        this.plazaID = plazaID;
    }

    public String getPlazaName() {
        return plazaName;
    }

    public void setPlazaName(String plazaName) {
        this.plazaName = plazaName;
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

    public double getCarFee() {
        return carFee;
    }

    public void setCarFee(int carFee) {
        this.carFee = carFee;
    }

    public double getBikeFee() {
        return bikeFee;
    }

    public void setBikeFee(int bikeFee) {
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

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isApprovedByAdmin() {
        return isApprovedByAdmin;
    }

    public void setApprovedByAdmin(boolean approvedByAdmin) {
        isApprovedByAdmin = approvedByAdmin;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


}
