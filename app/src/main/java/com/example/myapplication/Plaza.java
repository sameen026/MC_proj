package com.example.myapplication;

public class Plaza {
    String name;
    double longitude;
    double latitude;
    String area;
    int charges;


    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }


    public String getArea() {
        return area;
    }

    public Plaza(String name, double longitude, double latitude, String area) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

}
