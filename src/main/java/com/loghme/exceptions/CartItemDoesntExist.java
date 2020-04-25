package com.loghme.exceptions;

public class CartItemDoesntExist extends Exception {
    private String foodName;
    private String restaurantId;

    public CartItemDoesntExist(String foodName, String restaurantId) {
        this.foodName = foodName;
        this.restaurantId = restaurantId;
    }

    public String toString() {
        return String.format("There isn't cart item with food name %s and restaurant id %s", foodName, restaurantId);
    }
}
