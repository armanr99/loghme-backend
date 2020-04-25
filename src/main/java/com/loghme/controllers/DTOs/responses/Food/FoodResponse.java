package com.loghme.controllers.DTOs.responses.Food;

import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantSmallResponse;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Restaurant.Restaurant;

public class FoodResponse {
    private String name;
    private String description;
    private String image;
    private double popularity;
    private double price;
    private RestaurantSmallResponse restaurant;

    public FoodResponse(Restaurant restaurant, Food food) {
        this.restaurant = new RestaurantSmallResponse(restaurant);
        this.name = food.getName();
        this.description = food.getDescription();
        this.image = food.getImage();
        this.popularity = food.getPopularity();
        this.price = food.getPrice();
    }
}
