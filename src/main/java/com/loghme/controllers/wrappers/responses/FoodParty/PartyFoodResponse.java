package com.loghme.controllers.wrappers.responses.FoodParty;

import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantSmallResponse;
import com.loghme.models.Food.PartyFood;
import com.loghme.models.Restaurant.Restaurant;

public class PartyFoodResponse {
    private String name;
    private String description;
    private String image;
    private double popularity;
    private double price;
    private int count;
    private double oldPrice;
    private RestaurantSmallResponse restaurant;

    public PartyFoodResponse(Restaurant restaurant, PartyFood partyFood) {
        this.restaurant = new RestaurantSmallResponse(restaurant);
        this.name = partyFood.getName();
        this.description = partyFood.getDescription();
        this.image = partyFood.getImage();
        this.popularity = partyFood.getPopularity();
        this.price = partyFood.getPrice();
        this.count = partyFood.getCount();
        this.oldPrice = partyFood.getOldPrice();
    }
}
