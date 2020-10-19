package com.example.yu_map;


import java.io.Serializable;

public class MapPoint implements Serializable {
    private String Name;
    private double latitude;
    private double longitude;

    public MapPoint() {
    }

    public MapPoint(String Name2, double latitude2, double longitude2) {
        this.Name = Name2;
        this.latitude = latitude2;
        this.longitude = longitude2;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name2) {
        this.Name = Name2;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude2) {
        this.latitude = latitude2;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude2) {
        this.longitude = longitude2;
    }
}