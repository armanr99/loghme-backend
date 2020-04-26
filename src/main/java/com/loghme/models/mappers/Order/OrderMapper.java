package com.loghme.models.mappers.Order;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Order.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper extends Mapper<Order, Integer> implements IOrderMapper {
    private static OrderMapper instance = null;
    private static final String TABLE_NAME = "Orders";
    private static final String COLUMN_NAMES = "userId";

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

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   id INTEGER NOT NULL AUTO_INCREMENT,\n"
                                + "   userId INTEGER NOT NULL,\n"
                                + "   FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE,\n"
                                + "   PRIMARY KEY (id)\n"
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
        PreparedStatement st =
                con.prepareStatement(getInsertStatement(), Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, order.getUserId());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            int orderId = rs.getInt(1);
            order.setId(orderId);
        }

        closeStatement(con, st);
    }

    private String getInsertStatement() {
        return String.format("INSERT INTO %s (%s) VALUES (?);", TABLE_NAME, COLUMN_NAMES);
    }

    @Override
    public Order convertResultSetToObject(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int userId = rs.getInt(2);

        return new Order(id, userId);
    }

    public ArrayList<Order> findAll(int userId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindUserOrdersStatement());
        st.setInt(1, userId);

        return findAll(con, st);
    }

    private String getFindUserOrdersStatement() {
        return String.format("SELECT * FROM %s WHERE userId = ?;", TABLE_NAME);
    }
}
