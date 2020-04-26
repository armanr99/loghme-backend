package com.loghme.controllers.DTOs.responses.Restaurant;

import com.loghme.controllers.DTOs.responses.Food.FoodResponse;
import com.loghme.controllers.DTOs.responses.Food.PartyFoodResponse;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantResponse {
    private String id;
    private String name;
    private Location location;
    private String logo;
    private ArrayList<FoodResponse> menu;
    private ArrayList<PartyFoodResponse> foodPartyMenu;

    public RestaurantResponse(Restaurant restaurant) throws SQLException {
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

    private void setMenu(Restaurant restaurant) throws SQLException {
        menu = new ArrayList<>();

        for(Food food : restaurant.getMenu())
            menu.add(new FoodResponse(restaurant, food));
    }

    private void setFoodPartyMenu(Restaurant restaurant) throws SQLException {
        foodPartyMenu = new ArrayList<>();

        for(PartyFood partyFood : restaurant.getFoodPartyMenu())
            foodPartyMenu.add(new PartyFoodResponse(restaurant, partyFood));
    }
}
