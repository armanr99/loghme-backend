package com.loghme.Restaurant;

public class FoodAlreadyExistsInRestaurant extends Exception {

    private String foodName;
    private String restaurantName;


    public FoodAlreadyExistsInRestaurant(String foodName, String restaurantName) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
    }

    public String toString() {
        return String.format("FoodAlreadyExistsInRestaurant Exception %s already exists in %s", foodName, restaurantName);
    }
}
