package com.loghme.controllers.DTOs.responses.Food;

import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Restaurant.Restaurant;

public class PartyFoodResponse extends FoodResponse {
    private int count;
    private double oldPrice;

    public PartyFoodResponse(Restaurant restaurant, PartyFood partyFood) {
        super(restaurant, partyFood);
        this.count = partyFood.getCount();
        this.oldPrice = partyFood.getOldPrice();
    }
}
