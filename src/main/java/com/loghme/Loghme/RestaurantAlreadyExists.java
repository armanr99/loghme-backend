package com.loghme.Loghme;

public class RestaurantAlreadyExists extends Exception {
    private String restaurantName;

    RestaurantAlreadyExists(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String toString() {
        return String.format("RestaurantAlreadyExists: Restaurant with name: %s already exists", restaurantName);
    }

}
