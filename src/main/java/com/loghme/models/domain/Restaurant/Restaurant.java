package com.loghme.models.domain.Restaurant;

import com.google.gson.JsonObject;
import com.loghme.configs.Fields;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.exceptions.FoodAlreadyExistsInRestaurant;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
    private String id;
    private String name;
    private String logo;
    private Location location;
    private HashMap<String, Food> menu;
    private HashMap<String, PartyFood> foodPartyMenu;

    public Restaurant() {
        this.menu = new HashMap<>();
        this.foodPartyMenu = new HashMap<>();
    }

    public Restaurant(String id, String name, String logo, Location location) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.location = location;
    }

    public void addFoods(ArrayList<Food> menu) throws FoodAlreadyExistsInRestaurant {
        for (Food food : menu) {
            addFood(food);
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
        return foodPartyMenu.containsKey(foodName) ? foodPartyMenu.get(foodName) : menu.getOrDefault(foodName, null);
    }

    private void addFood(Food newFood) throws FoodAlreadyExistsInRestaurant {
        String newFoodName = newFood.getName();

        if (menu.containsKey(newFoodName))
            throw new FoodAlreadyExistsInRestaurant(newFood.getName(), this.name);

        newFood.setRestaurantId(this.id);
        menu.put(newFoodName, newFood);
    }

    public void addFood(JsonObject newFoodJsonObj) throws FoodAlreadyExistsInRestaurant {
        String foodName = newFoodJsonObj.get(Fields.NAME).getAsString();
        String foodDescription = newFoodJsonObj.get(Fields.DESCRIPTION).getAsString();
        String image = "";
        double foodPopularity = newFoodJsonObj.get(Fields.POPULARITY).getAsDouble();
        double foodPrice = newFoodJsonObj.get(Fields.PRICE).getAsDouble();
        Food newFood = new Food(foodName, foodDescription, image, foodPopularity, foodPrice);

        this.addFood(newFood);
    }

    public ArrayList<Food> getListMenu() {
        ArrayList<Food> foods = new ArrayList<>();

        for(Food food : menu.values())
            if(!foodPartyMenu.containsKey(food.getName()))
                foods.add(food);

        return foods;
    }

    public double getAverageFoodsPopulation() {
        double sum = 0;

        for(Food food : menu.values())
            sum += food.getPopularity();

        return (menu.size() == 0 ? 0 : sum / menu.size());
    }

    public void addPartyFoods(ArrayList<PartyFood> menu) {
        for (PartyFood food : menu) {
            this.foodPartyMenu.put(food.getName(), food);
        }
    }

    public void clearPartyFoods() {
        foodPartyMenu.clear();
    }

    public ArrayList<PartyFood> getListPartyFoodsMenu() {
        return new ArrayList<>(foodPartyMenu.values());
    }
}
