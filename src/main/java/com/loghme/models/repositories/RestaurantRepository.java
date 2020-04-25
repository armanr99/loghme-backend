package com.loghme.models.repositories;

import com.loghme.models.DTOs.Restaurant.FoodPartyRestaurantInput;
import com.loghme.models.DTOs.Restaurant.RestaurantInput;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.mappers.Restaurant.RestaurantMapper;

import java.sql.SQLException;
import java.util.Collection;

public class RestaurantRepository {
    private static RestaurantRepository instance = null;

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    public void addRestaurant(RestaurantInput restaurantInput) throws SQLException {
        Restaurant restaurant =
                new Restaurant(
                        restaurantInput.getId(),
                        restaurantInput.getName(),
                        restaurantInput.getLogo(),
                        restaurantInput.getLocation());
        addRestaurant(restaurant);

        FoodRepository.getInstance().addRestaurantFoods(restaurantInput);
    }

    public void addFoodPartyRestaurant(FoodPartyRestaurantInput foodPartyRestaurantInput)
            throws SQLException {
        Restaurant restaurant =
                new Restaurant(
                        foodPartyRestaurantInput.getId(),
                        foodPartyRestaurantInput.getName(),
                        foodPartyRestaurantInput.getLogo(),
                        foodPartyRestaurantInput.getLocation());
        addRestaurant(restaurant);

        PartyFoodRepository.getInstance().addRestaurantPartyFoods(foodPartyRestaurantInput);
    }

    private void addRestaurant(Restaurant restaurant) throws SQLException {
        RestaurantMapper.getInstance().insert(restaurant);
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
}
