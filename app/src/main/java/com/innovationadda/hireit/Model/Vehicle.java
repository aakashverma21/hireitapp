package com.innovationadda.hireit.Model;

public class Vehicle {

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getVehicleImageURL() {
        return vehicleImageURL;
    }

    public void setVehicleImageURL(String vehicleImageURL) {
        this.vehicleImageURL = vehicleImageURL;
    }

    private int vehicleID;
    private String vehicleName;
    private String price;
    private String detail;
    private String vehicleImageURL;

    public Vehicle( int vehicleID, String vehicleName, String price, String detail, String vehicleImageURL) {
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.price = price;
        this.detail = detail;
        this.vehicleImageURL = vehicleImageURL;
    }

}
