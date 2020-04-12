package com.loghme.models.Restaurant.exceptions;

public class FoodDoesntExist extends Exception {
    private String foodName;
    private String restaurantId;

    public FoodDoesntExist(String foodName, String restaurantId) {
        this.foodName = foodName;
        this.restaurantId = restaurantId;
    }

    public String toString() {
        return String.format("Restaurant with id %s does not have food named %s", restaurantId, foodName);
    }
}