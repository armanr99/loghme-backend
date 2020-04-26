package com.loghme.models.mappers.PartyFood;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.utils.PairKey.PairKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PartyFoodMapper extends Mapper<PartyFood, PairKey> implements IPartyFoodMapper {
    private static PartyFoodMapper instance = null;
    private static final String TABLE_NAME = "PartyFood";
    private static final String COLUMN_NAMES =
            "restaurantId, name, description, image, popularity, price, oldPrice, count";

    public static PartyFoodMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new PartyFoodMapper();
        }
        return instance;
    }

    private PartyFoodMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   restaurantId VARCHAR(255) NOT NULL,\n"
                                + "   name VARCHAR(255) NOT NULL,\n"
                                + "   description text,\n"
                                + "   image VARCHAR(255) NOT NULL,\n"
                                + "   popularity DOUBLE NOT NULL,\n"
                                + "   price DOUBLE NOT NULL,\n"
                                + "   oldPrice DOUBLE NOT NULL,\n"
                                + "   count INTEGER NOT NULL DEFAULT 0,\n"
                                + "   PRIMARY KEY (restaurantId, name),\n"
                                + "   FOREIGN KEY (restaurantId) REFERENCES Restaurant(id) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public PartyFood find(String restaurantId, String name) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setString(1, restaurantId);
        st.setString(2, name);

        return findOne(con, st);
    }

    public void insertBatch(ArrayList<PartyFood> partyFoods) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        for (PartyFood partyFood : partyFoods) {
            st.setString(1, partyFood.getRestaurantId());
            st.setString(2, partyFood.getName());
            st.setString(3, partyFood.getDescription());
            st.setString(4, partyFood.getImage());
            st.setDouble(5, partyFood.getPopularity());
            st.setDouble(6, partyFood.getPrice());
            st.setDouble(7, partyFood.getOldPrice());
            st.setInt(8, partyFood.getCount());
            st.addBatch();
        }

        executeUpdateBatch(con, st);
    }

    public ArrayList<PartyFood> findAll(String restaurantId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindRestaurantFoodsStatement());
        st.setString(1, restaurantId);

        return findAll(con, st);
    }

    private String getFindStatement() {
        return String.format("SELECT * FROM %s WHERE restaurantId = ? AND name = ?;", TABLE_NAME);
    }

    private String getInsertStatement() {
        return String.format(
                "INSERT IGNORE INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    private String getFindRestaurantFoodsStatement() {
        return String.format("SELECT * FROM %s WHERE restaurantId = ?;", TABLE_NAME);
    }

    @Override
    public PartyFood convertResultSetToObject(ResultSet rs) throws SQLException {
        String restaurantId = rs.getString(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        String image = rs.getString(4);
        double popularity = rs.getDouble(5);
        double price = rs.getDouble(6);
        double oldPrice = rs.getDouble(7);
        int count = rs.getInt(8);

        return new PartyFood(name, restaurantId, description, image, popularity, price, count, oldPrice);
    }
}
