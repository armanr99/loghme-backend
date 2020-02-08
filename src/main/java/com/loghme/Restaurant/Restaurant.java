package com.loghme.Restaurant;

import com.google.gson.JsonObject;
import com.loghme.Food.Food;
import com.loghme.Location.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Restaurant {
    private String name;
    private String description;
    private Location location;
    private HashMap<String, Food> menu;

    public Restaurant() {
        this.menu = new HashMap<>();
    }

    public Restaurant(String name, String description, Location location, ArrayList<Food> menu) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.menu = new HashMap<>();
        this.addFoods(menu);
    }

    public void addFoods(ArrayList<Food> menu) {
        for (Food food : menu) {
            this.menu.put(food.getName(), food);
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
        return menu.getOrDefault(foodName, null);
    }

    private void addFood(Food newFood) throws FoodAlreadyExistsInRestaurant {
        String newFoodName = newFood.getName();
        if (menu.containsKey(newFoodName))
            throw new FoodAlreadyExistsInRestaurant(newFood.getName(), this.name);
        menu.put(newFoodName, newFood);
    }

    public void addFood(JsonObject newFoodJsonObj) throws FoodAlreadyExistsInRestaurant {
        String foodName = newFoodJsonObj.get("name").getAsString();
        String foodDescription = newFoodJsonObj.get("description").getAsString();
        double foodPopularity = newFoodJsonObj.get("popularity").getAsDouble();
        double foodPrice = newFoodJsonObj.get("price").getAsDouble();
        Food newFood = new Food(foodName, foodDescription, foodPopularity, foodPrice);

        this.addFood(newFood);
    }

    public ArrayList<Food> getListMenu() {
        return new ArrayList<>(menu.values());
    }

    public HashMap<String, Food> getFoods() {
        return menu;
    }
}
