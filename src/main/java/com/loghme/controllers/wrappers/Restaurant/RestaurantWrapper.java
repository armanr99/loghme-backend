package com.loghme.controllers.wrappers.Restaurant;

import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Restaurant;

import java.util.ArrayList;

public class RestaurantWrapper {
    private String id;
    private String name;
    private Location location;
    private String logo;
    private ArrayList<Food> menu;
    private ArrayList<Food> foodPartyMenu;

    public RestaurantWrapper(Restaurant restaurant) {
        setInfo(restaurant);
        setFoodPartyMenu(restaurant);
    }

    private void setInfo(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.logo = restaurant.getLogo();
        this.menu = restaurant.getListMenu();
    }

    private void setFoodPartyMenu(Restaurant restaurant) {
        foodPartyMenu = new ArrayList<>();
        foodPartyMenu.addAll(restaurant.getListPartyFoodsMenu());
    }
}
