package com.loghme.models.mappers.Restaurant;

import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantMapper extends Mapper<Restaurant, String> implements IRestaurantMapper {
    private static RestaurantMapper instance = null;
    private static final String TABLE_NAME = "Restaurant";
    private static final String COLUMN_NAMES = " id, name, logo, posX, posY ";

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

        statements.add(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        statements.add(
                String.format(
                        "CREATE TABLE %s (\n"
                                + "   id VARCHAR(255) PRIMARY KEY,\n"
                                + "   name VARCHAR(255) NOT NULL,\n"
                                + "   logo VARCHAR(255) NOT NULL,\n"
                                + "   posX DOUBLE NOT NULL,\n"
                                + "   posY DOUBLE NOT NULL\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public String getFindStatement() {
        return String.format("SELECT * FROM %s WHERE id = ?;", TABLE_NAME);
    }

    public String getInsertStatement(Restaurant restaurant) {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%s, %s, %s, %f, %f",
                TABLE_NAME,
                COLUMN_NAMES,
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLogo(),
                restaurant.getLocation().getX(),
                restaurant.getLocation().getY());
    }

    public String getDeleteStatement() {
        return String.format("DELETE FROM %s WHERE id = ?;", TABLE_NAME);
    }

    @Override
    public Restaurant convertResultSetToObject(ResultSet rs) throws SQLException {
        String id = rs.getString(1);
        String name = rs.getString(2);
        String logo = rs.getString(3);
        Location location = new Location(rs.getDouble(4), rs.getDouble(5));
        return new Restaurant(id, name, logo, location);
    }
}
