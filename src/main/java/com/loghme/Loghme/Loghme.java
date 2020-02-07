package com.loghme.Loghme;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loghme.Restaurant.Restaurant;

import java.util.HashMap;

public class Loghme {
    private HashMap<String, Restaurant> restaurants;
    private Gson gson;

    public Loghme() {
        gson = new Gson();
    }

    public void addRestaurant(String serializedRestaurant) throws JsonSyntaxException, RestaurantAlreadyExists {
        Restaurant newRestaurant = gson.fromJson(serializedRestaurant, Restaurant.class);
        String newRestaurantName = newRestaurant.getName();

        if (restaurants.containsKey(newRestaurantName))
            throw new RestaurantAlreadyExists(newRestaurantName);
        restaurants.put(newRestaurantName, newRestaurant);
    }
}
