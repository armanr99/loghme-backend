package com.loghme.models.repositories;

import com.loghme.models.DTOs.Restaurant.FoodPartyRestaurantInput;
import com.loghme.models.DTOs.Restaurant.RestaurantInput;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.exceptions.RestaurantDoesntExist;

import java.util.Collection;
import java.util.HashMap;

public class RestaurantRepository {
    private HashMap<String, Restaurant> restaurants;
    private static RestaurantRepository instance = null;

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    private RestaurantRepository() {
        restaurants = new HashMap<>();
    }

    public void addRestaurant(RestaurantInput restaurantInput) {
        Restaurant restaurant =
                new Restaurant(
                        restaurantInput.getId(),
                        restaurantInput.getName(),
                        restaurantInput.getLogo(),
                        restaurantInput.getLocation());
        addRestaurant(restaurant);

        FoodRepository.getInstance().addRestaurantFoods(restaurantInput);
    }

    public void addFoodPartyRestaurant(FoodPartyRestaurantInput foodPartyRestaurantInput) {
        Restaurant restaurant =
                new Restaurant(
                        foodPartyRestaurantInput.getId(),
                        foodPartyRestaurantInput.getName(),
                        foodPartyRestaurantInput.getLogo(),
                        foodPartyRestaurantInput.getLocation());
        addRestaurant(restaurant);

        PartyFoodRepository.getInstance().addRestaurantPartyFoods(foodPartyRestaurantInput);
    }

    private void addRestaurant(Restaurant restaurant) {
        if (!restaurants.containsKey(restaurant.getId())) {
            restaurants.put(restaurant.getId(), restaurant);
        }
    }

    public Restaurant getRestaurant(String restaurantId) throws RestaurantDoesntExist {
        if (!restaurants.containsKey(restaurantId)) {
            throw new RestaurantDoesntExist(restaurantId);
        } else {
            return restaurants.get(restaurantId);
        }
    }

    public Collection<Restaurant> getRestaurants() {
        return restaurants.values();
    }
}
