package com.loghme.controllers.Restaurant.wrappers;

import com.loghme.models.Restaurant.Restaurant;

public class RestaurantWrapper {
    private SingleRestaurantWrapper restaurant;

    public RestaurantWrapper(Restaurant restaurant) {
        this.restaurant = new SingleRestaurantWrapper(restaurant);
    }
}
