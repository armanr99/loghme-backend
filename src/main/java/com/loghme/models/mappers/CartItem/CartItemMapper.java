package com.loghme.models.mappers.CartItem;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.CartItem.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItemMapper extends Mapper<CartItem> implements ICartItemMapper {
    private static CartItemMapper instance = null;
    private static final String TABLE_NAME = "CartItem";
    private static final String COLUMN_NAMES = "userId, restaurantId, foodName, count";

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

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   userId INTEGER NOT NULL,\n"
                                + "   restaurantId VARCHAR(255) NOT NULL,\n"
                                + "   foodName VARCHAR(255) NOT NULL,\n"
                                + "   count INTEGER NOT NULL,\n"
                                + "   PRIMARY KEY (userId, restaurantId, foodName),\n"
                                + "   FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public CartItem find(int userId, String restaurantId, String foodName) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setInt(1, userId);
        st.setString(2, restaurantId);
        st.setString(3, foodName);

        return findOne(con, st);
    }

    private String getFindStatement() {
        return String.format(
                "SELECT * FROM %s WHERE userId = ? AND restaurantId = ? AND foodName = ?;",
                TABLE_NAME);
    }

    public ArrayList<CartItem> findAll(int userId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindUserCartItemsStatement());
        st.setInt(1, userId);

        return findAll(con, st);
    }

    private String getFindUserCartItemsStatement() {
        return String.format("SELECT * FROM %s WHERE userId = ?;", TABLE_NAME);
    }

    public void insert(CartItem cartItem) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        st.setInt(1, cartItem.getUserId());
        st.setString(2, cartItem.getRestaurantId());
        st.setString(3, cartItem.getFoodName());
        st.setDouble(4, cartItem.getCount());

        executeUpdate(con, st);
    }

    private String getInsertStatement() {
        return String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    public void delete(int userId, String restaurantId, String foodName) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getDeleteStatement());

        st.setInt(1, userId);
        st.setString(2, restaurantId);
        st.setString(3, foodName);

        executeUpdate(con, st);
    }

    private String getDeleteStatement() {
        return String.format(
                "DELETE FROM %s WHERE userId = ? AND restaurantId = ? AND foodName = ?;",
                TABLE_NAME);
    }

    public void updateCount(int userId, String restaurantId, String foodName, int count)
            throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getUpdateCountStatement());

        st.setInt(1, count);
        st.setInt(2, userId);
        st.setString(3, restaurantId);
        st.setString(4, foodName);

        executeUpdate(con, st);
    }

    private String getUpdateCountStatement() {
        return String.format(
                "UPDATE %s SET count = ? WHERE userId = ? AND restaurantId = ? AND foodName = ?;",
                TABLE_NAME);
    }

    public CartItem findFirst(int userId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindFirstStatement());
        st.setInt(1, userId);

        return findOne(con, st);
    }

    private String getFindFirstStatement() {
        return String.format("SELECT * FROM %s WHERE userId = ? LIMIT 1;", TABLE_NAME);
    }

    public void delete(int userId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getDeleteUserCartStatement());
        st.setInt(1, userId);

        executeUpdate(con, st);
    }

    private String getDeleteUserCartStatement() {
        return String.format("DELETE FROM %s WHERE userId = ?;", TABLE_NAME);
    }

    @Override
    public CartItem convertResultSetToObject(ResultSet rs) throws SQLException {
        int userId = rs.getInt(1);
        String restaurantId = rs.getString(2);
        String foodName = rs.getString(3);
        int count = rs.getInt(4);

        CartItem cartItem = new CartItem(userId, restaurantId, foodName);
        cartItem.setCount(count);

        return cartItem;
    }
}
