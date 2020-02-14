package com.loghme.Restaurant.Exceptions;

public class RestaurantAlreadyExists extends Exception {
    private String restaurantID;

    public RestaurantAlreadyExists(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String toString() {
        return String.format("Restaurant with id: %s already exists", restaurantID);
    }
}
