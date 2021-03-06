package com.loghme.models.mappers.Restaurant;

import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

interface IRestaurantMapper {
    Restaurant find(String restaurantId) throws SQLException;
    void insertBatch(ArrayList<Restaurant> restaurants) throws SQLException;
    ArrayList<Restaurant> findAll(int limit, int offset) throws SQLException;
    ArrayList<Restaurant> search(String restaurantName, String foodName) throws SQLException;
}
