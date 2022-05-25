package com.innovationadda.hireit.Model;

public class summary {

    private int bookingID;
    private String vehicleName;
    private String price;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
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

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    private String returndate;
    private String pickupdate;

    public summary(int bookingID, String vehicleName, String price, String pickupdate, String returndate) {
        this.bookingID = bookingID;
        this.vehicleName = vehicleName;
        this.price = price;
        this.pickupdate = pickupdate;
        this.returndate = returndate;
    }

}
