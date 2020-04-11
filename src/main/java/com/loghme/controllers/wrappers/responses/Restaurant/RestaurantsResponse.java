package com.loghme.controllers.wrappers.responses.Restaurant;

import com.loghme.models.Restaurant.Restaurant;

import java.util.ArrayList;

public class RestaurantsResponse {
    private ArrayList<RestaurantSmallResponse> restaurants;

    public RestaurantsResponse(ArrayList<Restaurant> restaurants) {
        setRestaurants(restaurants);
    }

    private void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = new ArrayList<>();

        for(Restaurant restaurant : restaurants) {
            this.restaurants.add(new RestaurantSmallResponse(restaurant));
        }
    }
}
