package com.loghme.Restaurant;

import com.loghme.Food.Food;

import java.util.HashMap;
import java.util.List;

public class Restaurant {
    private String name;
    private String description;
    private Location location;
    private HashMap<String, Food> foodMenu;

    public Restaurant(String name, String description, Location location, List<Food> foodMenu) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.foodMenu = new HashMap<>();
        for (Food food : foodMenu) {
            this.foodMenu.put(food.getName(), food);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public Food getFood(String foodName) {
        return foodMenu.getOrDefault(foodName, null);
    }

    public void addFood(Food newFood) {
        String newFoodName = newFood.getName();
        if (foodMenu.containsKey(newFoodName))
            throw new FoodAlreadyExistsInRestaurant(newFood.getName(), this.name);
        foodMenu.put(newFoodName, newFood);
    }
}
