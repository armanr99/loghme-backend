package com.loghme.models.mappers.Restaurant;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.*;
import java.util.ArrayList;

public class RestaurantMapper extends Mapper<Restaurant, String> implements IRestaurantMapper {
    private static RestaurantMapper instance = null;
    private static final String TABLE_NAME = "Restaurant";
    private static final String COLUMN_NAMES = "id, name, logo, posX, posY";

    public static RestaurantMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new RestaurantMapper();
        }
        return instance;
    }

    private RestaurantMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   id VARCHAR(255) PRIMARY KEY,\n"
                                + "   name VARCHAR(1023) NOT NULL,\n"
                                + "   logo VARCHAR(1023) NOT NULL,\n"
                                + "   posX DOUBLE NOT NULL,\n"
                                + "   posY DOUBLE NOT NULL\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public Restaurant find(String restaurantId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setString(1, restaurantId);

        return findOne(con, st);
    }

    public ArrayList<Restaurant> findAll(int limit, int offset) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindAllStatement());
        st.setInt(1, limit);
        st.setInt(2, offset);

        return findAll(con, st);
    }

    public void insertBatch(ArrayList<Restaurant> restaurants) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        for (Restaurant restaurant : restaurants) {
            st.setString(1, restaurant.getId());
            st.setString(2, restaurant.getName());
            st.setString(3, restaurant.getLogo());
            st.setDouble(4, restaurant.getLocation().getX());
            st.setDouble(5, restaurant.getLocation().getY());
            st.addBatch();
        }

        executeUpdateBatch(con, st);
    }

    private String getFindStatement() {
        return String.format("SELECT * FROM %s WHERE id = ?;", TABLE_NAME);
    }

    private String getInsertStatement() {
        return String.format(
                "INSERT IGNORE INTO %s (%s) VALUES (?, ?, ?, ?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    private String getFindAllStatement() {
        return String.format("SELECT * FROM %s LIMIT ? OFFSET ?;", TABLE_NAME);
    }

    @Override
    public Restaurant convertResultSetToObject(ResultSet rs) throws SQLException {
        String id = rs.getString(1);
        String name = rs.getString(2);
        String logo = rs.getString(3);
        Location location = new Location(rs.getDouble(4), rs.getDouble(5));
        return new Restaurant(id, name, logo, location);
    }

    public ArrayList<Restaurant> search(String restaurantName, String foodName)
            throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getSearchStatement(restaurantName, foodName));

        return findAll(con, st);
    }

    private String getSearchStatement(String restaurantName, String foodName) {
        return String.format(
                "select distinct R.* from %s R, Food F where R.id = F.restaurantId and (R.name like '%%%s%%' and F.name like '%%%s%%');",
                TABLE_NAME, restaurantName, foodName);
    }
}
