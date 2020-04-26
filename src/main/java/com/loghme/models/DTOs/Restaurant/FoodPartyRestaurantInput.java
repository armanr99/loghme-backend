package com.loghme.models.DTOs.Restaurant;

import com.loghme.models.DTOs.Food.PartyFoodInput;
import com.loghme.models.domain.Location.Location;

import java.util.ArrayList;

public class FoodPartyRestaurantInput {
    private String id;
    private String name;
    private String logo;
    private Location location;
    private ArrayList<PartyFoodInput> menu;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<PartyFoodInput> getMenu() {
        return menu;
    }
}
