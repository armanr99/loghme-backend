package com.loghme.models.repositories;

import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantAlreadyExists;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RestaurantRepository {
    private HashMap<String, Restaurant> restaurants;
    private static RestaurantRepository instance = null;

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private RestaurantRepository() {
        restaurants = new HashMap<>();
    }

    public void addRestaurant(Restaurant restaurant) throws RestaurantAlreadyExists {
        if (restaurants.containsKey(restaurant.getId()))
            throw new RestaurantAlreadyExists(restaurant.getId());
        else
            restaurants.put(restaurant.getId(), restaurant);
    }

    public Restaurant getRestaurant(String restaurantId) throws RestaurantDoesntExist {
        if (!restaurants.containsKey(restaurantId))
            throw new RestaurantDoesntExist(restaurantId);
        else
            return restaurants.get(restaurantId);
    }

    public List<String> getRestaurantsStr() {
        return new ArrayList<>(restaurants.keySet());
    }

    public Collection<Restaurant> getRestaurants() {
        return restaurants.values();
    }
}
