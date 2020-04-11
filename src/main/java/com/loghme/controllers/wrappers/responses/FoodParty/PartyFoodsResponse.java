package com.loghme.controllers.wrappers.responses.FoodParty;

import com.loghme.models.Food.PartyFood;
import com.loghme.models.Restaurant.Restaurant;

import java.util.ArrayList;

public class PartyFoodsResponse {
    ArrayList<PartyFoodResponse> partyFoods;

    public PartyFoodsResponse(ArrayList<Restaurant> restaurants) {
        partyFoods = new ArrayList<>();

        for(Restaurant restaurant : restaurants)
            addRestaurantPartyFoods(restaurant);
    }

    private void addRestaurantPartyFoods(Restaurant restaurant) {
        for(PartyFood partyFood : restaurant.getListPartyFoodsMenu())
            partyFoods.add(new PartyFoodResponse(restaurant, partyFood));
    }
}
