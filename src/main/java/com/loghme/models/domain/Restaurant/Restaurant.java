package com.loghme.models.domain.Restaurant;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.repositories.FoodRepository;
import com.loghme.models.repositories.PartyFoodRepository;
import com.loghme.models.services.RestaurantService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Restaurant {
    private String id;
    private String name;
    private String logo;
    private Location location;

    public Restaurant(String id, String name, String logo, Location location) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.location = location;
    }

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

    public ArrayList<Food> getMenu() throws SQLException {
        return FoodRepository.getInstance().getFoods(this.id);
    }

    public ArrayList<PartyFood> getFoodPartyMenu() {
        return PartyFoodRepository.getInstance().getPartyFoods(this.id);
    }

    public Food getFood(String foodName) throws FoodDoesntExist, SQLException {
        return RestaurantService.getInstance().getFood(this.id, foodName);
    }
}
