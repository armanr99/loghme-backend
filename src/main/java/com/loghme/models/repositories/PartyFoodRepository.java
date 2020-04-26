package com.loghme.models.repositories;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.mappers.PartyFood.PartyFoodMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class PartyFoodRepository {
    private static PartyFoodRepository instance = null;

    public static PartyFoodRepository getInstance() {
        if (instance == null) {
            instance = new PartyFoodRepository();
        }
        return instance;
    }

    public void addPartyFoods(ArrayList<PartyFood> partyFoods) throws SQLException {
        PartyFoodMapper.getInstance().insertBatch(partyFoods);
    }

    public PartyFood getPartyFood(String restaurantId, String foodName) throws FoodDoesntExist, SQLException {
        PartyFood partyFood = PartyFoodMapper.getInstance().find(restaurantId, foodName);

        if(partyFood == null)
            throw new FoodDoesntExist(foodName, restaurantId);
        else
            return partyFood;
    }

    public ArrayList<PartyFood> getPartyFoods(String restaurantId) throws SQLException {
        return PartyFoodMapper.getInstance().findAll(restaurantId);
    }

    public ArrayList<PartyFood> getPartyFoods() throws SQLException {
        return PartyFoodMapper.getInstance().findAll();
    }

    public void deletePartyFoods() throws SQLException {
        PartyFoodMapper.getInstance().deleteAll();
    }

    public void updateCount(String restaurantId, String foodName, int count) throws SQLException {
        PartyFoodMapper.getInstance().updateCount(restaurantId, foodName, count);
    }
}
