package com.loghme.exceptions;

public class DifferentRestaurant extends Exception {
    private String cartRestaurantId;

    public DifferentRestaurant(String cartRestaurantId) {
        this.cartRestaurantId = cartRestaurantId;
    }

    public String toString() {
        return String.format("There are foods from different restaurant with id %s in cart", cartRestaurantId);
    }
}
