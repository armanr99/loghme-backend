package com.loghme.Loghme;

public class RestaurantDoesntExist extends Exception {
    private String invalidRestaurantName;

    RestaurantDoesntExist(String invalidRestaurantName) {
        this.invalidRestaurantName = invalidRestaurantName;
    }

    public String toString() {
        return String.format("RestaurantDoesntExist: Restaurant with name %s does not exist", invalidRestaurantName);
    }
}
