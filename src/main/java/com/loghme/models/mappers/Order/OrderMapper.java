package com.loghme.models.mappers.Order;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Order.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderMapper extends Mapper<Order, Integer> implements IOrderMapper {
    private static OrderMapper instance = null;
    private static final String TABLE_NAME = "Order";
    private static final String COLUMN_NAMES = "id, userId";

    public static OrderMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderMapper();
        }
        return instance;
    }

    private OrderMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        statements.add(
                String.format(
                        "CREATE TABLE %s (\n"
                                + "   id INTEGER NOT NULL,\n"
                                + "   userId INTEGER NOT NULL,\n"
                                + "   FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public Order find(int orderId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setInt(1, orderId);

        return findOne(con, st);
    }

    private String getFindStatement() {
        return String.format("SELECT * FROM %s WHERE id = ?;", TABLE_NAME);
    }

    public void insert(Order order) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        st.setInt(1, order.getId());
        st.setInt(2, order.getUserId());

        executeUpdate(con, st);
    }

    private String getInsertStatement() {
        return String.format("INSERT INTO %s (%s) VALUES (?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    @Override
    public Order convertResultSetToObject(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int userId = rs.getInt(2);

        return new Order(id, userId);
    }
}
