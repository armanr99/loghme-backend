package com.loghme.controllers.DTOs.responses.FoodParty;

import com.loghme.controllers.DTOs.responses.Food.PartyFoodResponse;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodPartyResponse {
    ArrayList<PartyFoodResponse> partyFoods;

    public FoodPartyResponse(ArrayList<PartyFood> inPartyFoods) throws RestaurantDoesntExist, SQLException {
        partyFoods = new ArrayList<>();

        for (PartyFood partyFood : inPartyFoods) {
            Restaurant restaurant = partyFood.getRestaurant();
            partyFoods.add(new PartyFoodResponse(restaurant, partyFood));
        }
    }
}
