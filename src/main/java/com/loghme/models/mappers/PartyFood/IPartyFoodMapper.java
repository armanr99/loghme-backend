package com.loghme.models.mappers.PartyFood;

import com.loghme.models.domain.Food.PartyFood;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPartyFoodMapper {
    void insertBatch(ArrayList<PartyFood> partyFoods) throws SQLException;
    PartyFood find(String restaurantId, String name) throws SQLException;
    ArrayList<PartyFood> findAll(String restaurantId) throws SQLException;
    void updateCount(String restaurantId, String name, int count) throws SQLException;
    ArrayList<PartyFood> findAll() throws SQLException;
    void deleteAll() throws SQLException;
}
