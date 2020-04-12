package com.loghme.models.Restaurant.exceptions;

public class RestaurantAlreadyExists extends Exception {
    private String restaurantId;

    public RestaurantAlreadyExists(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String toString() {
        return String.format("Restaurant with id: %s already exists", restaurantId);
    }
}
