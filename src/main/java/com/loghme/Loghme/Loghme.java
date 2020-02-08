package com.loghme.Loghme;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.loghme.Food.Food;
import com.loghme.Restaurant.FoodAlreadyExistsInRestaurant;
import com.loghme.Restaurant.Restaurant;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loghme {
    private HashMap<String, Restaurant> restaurants;
    private Gson gson;

    public Loghme() {
        gson = new Gson();
        restaurants = new HashMap<>();
    }

    public void addRestaurant(String serializedRestaurant) throws JsonSyntaxException, RestaurantAlreadyExists {
        JsonObject restaurantObject = gson.fromJson(serializedRestaurant, JsonObject.class);
        JsonElement menuElement = restaurantObject.remove("menu");
        Restaurant newRestaurant = gson.fromJson(restaurantObject.toString(), Restaurant.class);
        String newRestaurantName = newRestaurant.getName();

        if (restaurants.containsKey(newRestaurantName))
            throw new RestaurantAlreadyExists(newRestaurantName);

        Type listType = new TypeToken<List<Food>>() {}.getType();
        ArrayList<Food> menu = gson.fromJson(menuElement, listType);
        newRestaurant.addFoods(menu);

        restaurants.put(newRestaurantName, newRestaurant);
    }

    public void addFood(String serializedFood)
            throws JsonParseException, FoodAlreadyExistsInRestaurant, RestaurantDoesntExist {
        JsonObject foodWithRestaurantName = gson.fromJson(serializedFood, JsonObject.class);
        JsonElement restaurantNameElement = foodWithRestaurantName.remove("restaurantName");
        String restaurantName = restaurantNameElement.isJsonNull() ? "" : restaurantNameElement.getAsString();

        if (!restaurants.containsKey(restaurantName))
            throw new RestaurantDoesntExist(restaurantName);

        restaurants.get(restaurantName).addFood(foodWithRestaurantName);
    }

    public List<String> getRestaurants() {
        return new ArrayList<>(restaurants.keySet());
    }

    public String getRestaurant(String restaurantInfo) throws JsonParseException, RestaurantDoesntExist {
        JsonElement restaurantNameElement = gson.fromJson(restaurantInfo, JsonObject.class).get("name");
        String restaurantName = restaurantNameElement.isJsonNull() ? "" : restaurantNameElement.getAsString();

        if (!restaurants.containsKey(restaurantName))
            throw new RestaurantDoesntExist(restaurantName);

        return gson.toJson(restaurants.get(restaurantName));
    }

    public String getFood(String foodInfo) throws JsonParseException, RestaurantDoesntExist {
        JsonElement restaurantNameElement = gson.fromJson(foodInfo, JsonObject.class).get("restaurantName");
        JsonElement foodNameElement = gson.fromJson(foodInfo, JsonObject.class).get("foodName");
        String restaurantName = restaurantNameElement.isJsonNull() ? "" : restaurantNameElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? "" : foodNameElement.getAsString();

        if (!restaurants.containsKey(restaurantName))
            throw new RestaurantDoesntExist(restaurantName);

        return gson.toJson(restaurants.get(restaurantName).getFood(foodName));
    }

    public void addToCart(String foodInfo) {
        throw new NotImplementedException();
    }

    public String getCart() {
        throw new NotImplementedException();
    }

    public String getRecommendedRestaurants() {
        throw new NotImplementedException();
    }
}
