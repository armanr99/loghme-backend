package com.loghme.models.mappers.CartItem;

import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.utils.TripleKey.TripleKey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItemMapper extends Mapper<CartItem, TripleKey> implements ICartItemMapper {
    private static CartItemMapper instance = null;
    private static final String TABLE_NAME = "CartItem";
    private static final String COLUMN_NAMES = " userId, restaurantId, foodName, count ";

    public static CartItemMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new CartItemMapper();
        }
        return instance;
    }

    private CartItemMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        statements.add(
                String.format(
                        "CREATE TABLE %s (\n"
                                + "   userId INTEGER NOT NULL,\n"
                                + "   restaurantId VARCHAR(255) NOT NULL,\n"
                                + "   foodName VARCHAR(255) NOT NULL,\n"
                                + "   count INTEGER NOT NULL,\n"
                                + "   PRIMARY KEY (userId, restaurantId, foodName),\n"
                                + "   FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE,\n"
                                + "   FOREIGN KEY (restaurantId, foodName) REFERENCES Food(restaurantId, name)\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    @Override
    public String getFindStatement(TripleKey id) {
        return String.format(
                "SELECT * FROM %s WHERE userId = %s AND restaurantId = %s AND foodName = %s;",
                TABLE_NAME,
                id.getFirstKeyStr(),
                id.getSecondKeyAsStr(),
                id.getThirdKeyAsStr());
    }

    @Override
    public String getInsertStatement(CartItem cartItem) {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%d, %s, %s, %d);",
                TABLE_NAME,
                COLUMN_NAMES,
                cartItem.getUserId(),
                cartItem.getRestaurantId(),
                cartItem.getFoodName(),
                cartItem.getCount());
    }

    @Override
    public String getDeleteStatement(TripleKey id) {
        return String.format(
                "DELETE FROM %s WHERE WHERE userId = %s AND restaurantId = %s AND foodName = %s;",
                TABLE_NAME,
                id.getFirstKeyStr(),
                id.getSecondKeyAsStr(),
                id.getThirdKeyAsStr());
    }

    @Override
    public CartItem convertResultSetToObject(ResultSet rs) throws SQLException {
        int userId = rs.getInt(1);
        String restaurantId = rs.getString(2);
        String foodName = rs.getString(3);
        int count = rs.getInt(4);

        return new CartItem(userId, restaurantId, foodName, count);
    }
}
