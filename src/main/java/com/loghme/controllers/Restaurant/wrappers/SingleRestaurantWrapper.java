package com.loghme.controllers.Restaurant.wrappers;

import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Restaurant;

import java.util.ArrayList;

public class SingleRestaurantWrapper {
    private String id;
    private String name;
    private Location location;
    private String logo;
    private ArrayList<FoodWrapper> menu;
    private ArrayList<FoodWrapper> foodPartyMenu;

    public SingleRestaurantWrapper(Restaurant restaurant) {
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

        for(Food food : restaurant.getListMenu()) {
            menu.add(new FoodWrapper(food));
        }
    }

    private void setFoodPartyMenu(Restaurant restaurant) {
        foodPartyMenu = new ArrayList<>();

        for(Food food : restaurant.getListPartyFoodsMenu()) {
            foodPartyMenu.add(new FoodWrapper(food));
        }
    }
}
