package com.loghme.models.Restaurant.Exceptions;

public class RestaurantDoesntExist extends Exception {
    private String invalidRestaurantId;

    public RestaurantDoesntExist(String invalidRestaurantName) {
        this.invalidRestaurantId = invalidRestaurantName;
    }

    public String toString() {
        return String.format("Restaurant with id %s does not exist", invalidRestaurantId);
    }
}