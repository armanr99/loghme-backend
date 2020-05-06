package com.loghme.controllers.DTOs.responses.Restaurant;

import com.loghme.models.domain.Restaurant.Restaurant;

import java.util.ArrayList;

public class RestaurantsResponse {
    private ArrayList<RestaurantSmallResponse> restaurants;

    public RestaurantsResponse(ArrayList<Restaurant> restaurants) {
        setRestaurants(restaurants);
    }

    private void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            this.restaurants.add(new RestaurantSmallResponse(restaurant));
        }
    }
}
