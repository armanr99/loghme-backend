package com.loghme.controllers.wrappers.responses.Food;

import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantSmallResponse;
import com.loghme.models.Food.Food;
import com.loghme.models.Restaurant.Restaurant;

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
