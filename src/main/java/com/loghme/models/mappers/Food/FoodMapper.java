package com.loghme.models.mappers.Food;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Food.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodMapper extends Mapper<Food> implements IFoodMapper {
    private static FoodMapper instance = null;
    private static final String TABLE_NAME = "Food";
    private static final String COLUMN_NAMES =
            "restaurantId, name, description, image, popularity, price";

    public static FoodMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new FoodMapper();
        }
        return instance;
    }

    private FoodMapper() throws SQLException {
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
                                + "   PRIMARY KEY (restaurantId, name),\n"
                                + "   FOREIGN KEY (restaurantId) REFERENCES Restaurant(id) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public Food find(String restaurantId, String name) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setString(1, restaurantId);
        st.setString(2, name);

        return findOne(con, st);
    }

    public void insertBatch(ArrayList<Food> foods) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        for (Food food : foods) {
            st.setString(1, food.getRestaurantId());
            st.setString(2, food.getName());
            st.setString(3, food.getDescription());
            st.setString(4, food.getImage());
            st.setDouble(5, food.getPopularity());
            st.setDouble(6, food.getPrice());
            st.addBatch();
        }

        executeUpdateBatch(con, st);
    }

    public ArrayList<Food> findAll(String restaurantId) throws SQLException {
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
                "INSERT IGNORE INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    private String getFindRestaurantFoodsStatement() {
        return String.format("SELECT * FROM %s WHERE restaurantId = ?;", TABLE_NAME);
    }

    @Override
    public Food convertResultSetToObject(ResultSet rs) throws SQLException {
        String restaurantId = rs.getString(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        String image = rs.getString(4);
        double popularity = rs.getDouble(5);
        double price = rs.getDouble(6);

        return new Food(name, restaurantId, description, image, popularity, price);
    }
}
