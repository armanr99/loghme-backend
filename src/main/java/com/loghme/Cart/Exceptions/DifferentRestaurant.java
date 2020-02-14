package com.loghme.Cart.Exceptions;

public class DifferentRestaurant extends Exception {
    private String cartRestaurantName;

    public DifferentRestaurant(String cartRestaurantName) {
        this.cartRestaurantName = cartRestaurantName;
    }

    public String toString() {
        return String.format("There are foods from different restaurant named %s in cart", cartRestaurantName);
    }
}
