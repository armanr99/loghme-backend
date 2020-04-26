package com.loghme.models.DTOs.Restaurant;

import com.loghme.models.DTOs.Food.FoodInput;
import com.loghme.models.domain.Location.Location;

import java.util.ArrayList;

public class RestaurantInput {
    private String id;
    private String name;
    private String logo;
    private Location location;
    private ArrayList<FoodInput> menu;

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

    public ArrayList<FoodInput> getMenu() {
        return menu;
    }
}
