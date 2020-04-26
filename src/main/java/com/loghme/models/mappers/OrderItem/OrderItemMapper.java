package com.loghme.models.mappers.OrderItem;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.OrderItem.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemMapper extends Mapper<OrderItem> implements IOrderItemMapper {
    private static OrderItemMapper instance = null;
    private static final String TABLE_NAME = "OrderItem";
    private static final String COLUMN_NAMES = "orderId, restaurantId, foodName, count";

    public static OrderItemMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderItemMapper();
        }
        return instance;
    }

    private OrderItemMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   orderId INTEGER NOT NULL,\n"
                                + "   restaurantId VARCHAR(255) NOT NULL,\n"
                                + "   foodName VARCHAR(255) NOT NULL,\n"
                                + "   count INTEGER NOT NULL,\n"
                                + "   PRIMARY KEY (orderId, restaurantId, foodName),\n"
                                + "   FOREIGN KEY (orderId) REFERENCES Orders(id) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public OrderItem find(int orderId, String restaurantId, String foodName) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setInt(1, orderId);
        st.setString(2, restaurantId);
        st.setString(3, foodName);

        return findOne(con, st);
    }

    private String getFindStatement() {
        return String.format(
                "SELECT * FROM %s WHERE orderId = ? AND restaurantId = ? AND foodName = ?;",
                TABLE_NAME);
    }

    public ArrayList<OrderItem> findAll(int orderId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindOrderItemsStatement());
        st.setInt(1, orderId);

        return findAll(con, st);
    }

    private String getFindOrderItemsStatement() {
        return String.format("SELECT * FROM %s WHERE orderId = ?;", TABLE_NAME);
    }

    public void insertBatch(ArrayList<OrderItem> orderItems) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        for (OrderItem orderItem : orderItems) {
            st.setInt(1, orderItem.getOrderId());
            st.setString(2, orderItem.getRestaurantId());
            st.setString(3, orderItem.getFoodName());
            st.setDouble(4, orderItem.getCount());
            st.addBatch();
        }

        executeUpdateBatch(con, st);
    }

    private String getInsertStatement() {
        return String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    public OrderItem findFirst(int orderId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindFirstStatement());
        st.setInt(1, orderId);

        return findOne(con, st);
    }

    private String getFindFirstStatement() {
        return String.format("SELECT * FROM %s WHERE orderId = ? LIMIT 1;", TABLE_NAME);
    }

    @Override
    public OrderItem convertResultSetToObject(ResultSet rs) throws SQLException {
        int orderId = rs.getInt(1);
        String restaurantId = rs.getString(2);
        String foodName = rs.getString(3);
        int count = rs.getInt(4);

        return new OrderItem(orderId, restaurantId, foodName, count);
    }
}

