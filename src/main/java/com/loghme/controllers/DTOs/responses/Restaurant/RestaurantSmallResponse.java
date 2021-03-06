package com.loghme.controllers.DTOs.responses.Restaurant;

import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.Restaurant;

public class RestaurantSmallResponse {
    private String id;
    private String name;
    private Location location;
    private String logo;

    public RestaurantSmallResponse(Restaurant restaurant) {
        setInfo(restaurant);
    }

    private void setInfo(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.logo = restaurant.getLogo();
    }
}
