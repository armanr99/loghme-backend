package com.loghme.controllers.Restaurant.wrappers;

import com.loghme.models.Restaurant.Restaurant;

import java.util.ArrayList;

public class RestaurantsWrapper {
    private ArrayList<RestaurantWrapper> restaurants;

    public RestaurantsWrapper(ArrayList<Restaurant> restaurants) {
        setRestaurants(restaurants);
    }

    private void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = new ArrayList<>();

        for(Restaurant restaurant : restaurants) {
            this.restaurants.add(new RestaurantWrapper(restaurant));
        }
    }
}
