package com.loghme.models.repositories;

import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.mappers.Restaurant.RestaurantMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class RestaurantRepository {
    private static RestaurantRepository instance = null;

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    public void addRestaurants(ArrayList<Restaurant> restaurants) throws SQLException {
        RestaurantMapper.getInstance().insertBatch(restaurants);
    }

    public Restaurant getRestaurant(String restaurantId)
            throws RestaurantDoesntExist, SQLException {
        Restaurant restaurant = RestaurantMapper.getInstance().find(restaurantId);

        if (restaurant == null) {
            throw new RestaurantDoesntExist(restaurantId);
        } else {
            return restaurant;
        }
    }

    public Collection<Restaurant> getRestaurants() throws SQLException {
        return RestaurantMapper.getInstance().findAll();
    }

    public ArrayList<Restaurant> searchRestaurants(String restaurantName, String foodName) throws SQLException {
        return RestaurantMapper.getInstance().search(restaurantName, foodName);
    }
}
