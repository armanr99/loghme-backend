package com.loghme.Restaurant;

import com.google.gson.JsonObject;
import com.loghme.Food.Food;
import com.loghme.Location.Location;

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

    private void addFood(Food newFood) throws FoodAlreadyExistsInRestaurant {
        String newFoodName = newFood.getName();
        if (foodMenu.containsKey(newFoodName))
            throw new FoodAlreadyExistsInRestaurant(newFood.getName(), this.name);
        foodMenu.put(newFoodName, newFood);
    }

    public void addFood(JsonObject newFoodJsonObj) throws FoodAlreadyExistsInRestaurant{
        String foodName = newFoodJsonObj.get("name").getAsString();
        String foodDescription = newFoodJsonObj.get("description").getAsString();
        double foodPopularity = newFoodJsonObj.get("popularity").getAsDouble();
        double foodPrice = newFoodJsonObj.get("price").getAsDouble();
        Food newFood = new Food(foodName, foodDescription, foodPopularity, foodPrice);

        this.addFood(newFood);
    }
}
