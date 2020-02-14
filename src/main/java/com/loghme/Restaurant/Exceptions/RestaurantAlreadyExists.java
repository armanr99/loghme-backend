package com.loghme.Restaurant.Exceptions;

public class RestaurantAlreadyExists extends Exception {
    private String restaurantName;

    public RestaurantAlreadyExists(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String toString() {
        return String.format("Restaurant with name: %s already exists", restaurantName);
    }
}
