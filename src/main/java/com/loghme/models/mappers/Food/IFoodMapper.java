package com.loghme.models.mappers.Food;

import com.loghme.models.domain.Food.Food;

import java.sql.SQLException;
import java.util.ArrayList;

interface IFoodMapper {
    void insertBatch(ArrayList<Food> foods) throws SQLException;
    Food find(String restaurantId, String name) throws SQLException;
    ArrayList<Food> findAll(String restaurantId) throws SQLException;
}
