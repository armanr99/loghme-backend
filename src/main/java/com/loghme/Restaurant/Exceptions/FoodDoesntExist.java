package com.loghme.Restaurant.Exceptions;

public class FoodDoesntExist extends Exception {
    private String foodName;
    private String restaurantName;

    public FoodDoesntExist(String foodName, String restaurantName) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
    }

    public String toString() {
        return String.format("Restaurant with name %s does not have food named %s", restaurantName, foodName);
    }
}
