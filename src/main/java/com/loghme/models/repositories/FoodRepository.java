package com.loghme.models.repositories;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.mappers.Food.FoodMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodRepository {
    private static FoodRepository instance = null;

    public static FoodRepository getInstance() {
        if (instance == null) {
            instance = new FoodRepository();
        }
        return instance;
    }

    public void addFoods(ArrayList<Food> foods) throws SQLException {
        FoodMapper.getInstance().insertBatch(foods);
    }

    public Food getFood(String restaurantId, String foodName) throws FoodDoesntExist, SQLException {
        Food food = FoodMapper.getInstance().find(restaurantId, foodName);

        if(food == null)
            throw new FoodDoesntExist(foodName, restaurantId);
        else
            return food;
    }

    public ArrayList<Food> getFoods(String restaurantId) throws SQLException {
        return FoodMapper.getInstance().findAll(restaurantId);
    }
}
