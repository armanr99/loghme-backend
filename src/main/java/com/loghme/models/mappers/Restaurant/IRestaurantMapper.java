package com.loghme.models.mappers.Restaurant;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

interface IRestaurantMapper extends IMapper<Restaurant, String> {
    Restaurant find(String restaurantId) throws SQLException;
    void insertBatch(ArrayList<Restaurant> restaurants) throws SQLException;
    ArrayList<Restaurant> findAll() throws SQLException;
}
