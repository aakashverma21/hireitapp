package com.innovationadda.hireit.Model;

import androidx.annotation.NonNull;

public class VehicleCategory {
    private int categoryID;
    private String category;
    private int quantity;
    private int colorCard;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getColorCard() {
        return colorCard;
    }

    public void setColorCard(int colorCard) {
        this.colorCard = colorCard;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    private String categoryImageURL;

    public VehicleCategory(String category, int categoryID, int colorCard, String categoryImageURL) {
        this.categoryID = categoryID;
        this.quantity = 10;
        this.category = category.toLowerCase();
        this.colorCard = colorCard;
        this.categoryImageURL = categoryImageURL;
    }

}



