package com.loghme.controllers.wrappers.responses.Restaurant;

import com.loghme.controllers.wrappers.responses.Food.FoodResponse;
import com.loghme.controllers.wrappers.responses.FoodParty.PartyFoodResponse;
import com.loghme.models.Food.Food;
import com.loghme.models.Food.PartyFood;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Restaurant;

import java.util.ArrayList;

public class RestaurantResponse {
    private String id;
    private String name;
    private Location location;
    private String logo;
    private ArrayList<FoodResponse> menu;
    private ArrayList<PartyFoodResponse> foodPartyMenu;

    public RestaurantResponse(Restaurant restaurant) {
        setInfo(restaurant);
        setMenu(restaurant);
        setFoodPartyMenu(restaurant);
    }

    private void setInfo(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.logo = restaurant.getLogo();
    }

    private void setMenu(Restaurant restaurant) {
        menu = new ArrayList<>();

        for(Food food : restaurant.getListMenu())
            menu.add(new FoodResponse(restaurant, food));
    }

    private void setFoodPartyMenu(Restaurant restaurant) {
        foodPartyMenu = new ArrayList<>();

        for(PartyFood partyFood : restaurant.getListPartyFoodsMenu())
            foodPartyMenu.add(new PartyFoodResponse(restaurant, partyFood));
    }
}
