package com.loghme.models.mappers.Food;

import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.utils.PairKey.PairKey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodMapper extends Mapper<Food, PairKey> implements IFoodMapper {
    private static FoodMapper instance = null;
    private static final String TABLE_NAME = "Food";
    private static final String COLUMN_NAMES =
            " restaurantId, name, description, image, popularity, price ";

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

        statements.add(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        statements.add(
                String.format(
                        "CREATE TABLE %s (\n"
                                + "   restaurantId VARCHAR(255) NOT NULL,\n"
                                + "   name VARCHAR(255) NOT NULL,\n"
                                + "   description VARCHAR(255),\n"
                                + "   image VARCHAR(255) NOT NULL,\n"
                                + "   popularity DOUBLE NOT NULL,\n"
                                + "   price DOUBLE NOT NULL,\n"
                                + "   PRIMARY KEY (restaurantId, name),\n"
                                + "   FOREIGN KEY (restaurantId) REFERENCES Restaurant(restaurantId) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    @Override
    public String getFindStatement(PairKey id) {
        return String.format(
                "SELECT * FROM %s WHERE restaurantId = %s AND name = %s;",
                TABLE_NAME, id.getFirstKeyStr(), id.getSecondKeyStr());
    }

    @Override
    public String getInsertStatement(Food food) {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%s, %s, %s, %s, %f, %f);",
                TABLE_NAME,
                COLUMN_NAMES,
                food.getRestaurantId(),
                food.getName(),
                food.getDescription(),
                food.getImage(),
                food.getPopularity(),
                food.getPrice());
    }

    @Override
    public String getDeleteStatement(PairKey id) {
        return String.format(
                "DELETE FROM %s WHERE restaurantId = %s AND name = %s;",
                TABLE_NAME, id.getFirstKeyStr(), id.getSecondKeyStr());
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
