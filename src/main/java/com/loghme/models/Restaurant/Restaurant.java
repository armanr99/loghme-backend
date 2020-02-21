package com.loghme.models.Restaurant;

import com.google.gson.JsonObject;
import com.loghme.configs.Fields;
import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Exceptions.FoodAlreadyExistsInRestaurant;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
    private String id;
    private String name;
    private Location location;
    private String logo;
    private HashMap<String, Food> menu;

    public Restaurant() {
        this.menu = new HashMap<>();
    }

    public void addFoods(ArrayList<Food> menu) {
        for (Food food : menu) {
            this.menu.put(food.getName(), food);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
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
        String foodName = newFoodJsonObj.get(Fields.NAME).getAsString();
        String foodDescription = newFoodJsonObj.get(Fields.DESCRIPTION).getAsString();
        double foodPopularity = newFoodJsonObj.get(Fields.POPULARITY).getAsDouble();
        double foodPrice = newFoodJsonObj.get(Fields.PRICE).getAsDouble();
        Food newFood = new Food(foodName, foodDescription, foodPopularity, foodPrice);

        this.addFood(newFood);
    }

    public ArrayList<Food> getListMenu() {
        return new ArrayList<>(menu.values());
    }

    public double getAverageFoodsPopulation() {
        double sum = 0;

        for(Food food : menu.values())
            sum += food.getPopularity();

        return (menu.size() == 0 ? 0 : sum / menu.size());
    }
}
