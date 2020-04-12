package com.loghme.controllers.wrappers.responses.Food;

import com.loghme.models.Food.PartyFood;
import com.loghme.models.Restaurant.Restaurant;

public class PartyFoodResponse extends FoodResponse {
    private int count;
    private double oldPrice;

    public PartyFoodResponse(Restaurant restaurant, PartyFood partyFood) {
        super(restaurant, partyFood);
        this.count = partyFood.getCount();
        this.oldPrice = partyFood.getOldPrice();
    }
}
