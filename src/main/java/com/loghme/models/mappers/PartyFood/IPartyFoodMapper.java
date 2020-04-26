package com.loghme.models.mappers.PartyFood;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.utils.PairKey.PairKey;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPartyFoodMapper extends IMapper<PartyFood, PairKey> {
    void insertBatch(ArrayList<PartyFood> partyFoods) throws SQLException;
    PartyFood find(String restaurantId, String name) throws SQLException;
    ArrayList<PartyFood> findAll(String restaurantId) throws SQLException;
}
