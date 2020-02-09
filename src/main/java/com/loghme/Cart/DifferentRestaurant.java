package com.loghme.Cart;

public class DifferentRestaurant extends Exception {
    private String cartRestaurantName;

    public DifferentRestaurant(String cartRestaurantName) {
        this.cartRestaurantName = cartRestaurantName;
    }

    public String toString() {
        return String.format("DifferentRestaurant Exception there are foods from different restaurant named %s in cart", cartRestaurantName);
    }
}
