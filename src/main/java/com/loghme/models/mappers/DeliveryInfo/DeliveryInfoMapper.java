package com.loghme.models.mappers.DeliveryInfo;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.DeliveryInfo.DeliveryInfo;
import com.loghme.models.domain.Location.Location;

import java.sql.*;
import java.util.ArrayList;

public class DeliveryInfoMapper extends Mapper<DeliveryInfo>
        implements IDeliveryInfoMapper {
    private static DeliveryInfoMapper instance = null;
    private static final String TABLE_NAME = "DeliveryInfo";
    private static final String COLUMN_NAMES =
            "orderId, deliveryId, velocity, posX, posY, startDate";

    public static DeliveryInfoMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new DeliveryInfoMapper();
        }
        return instance;
    }

    private DeliveryInfoMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   orderId INTEGER NOT NULL,\n"
                                + "   deliveryId VARCHAR(255) NOT NULL,\n"
                                + "   velocity double NOT NULL,\n"
                                + "   posX double NOT NULL,\n"
                                + "   posY double NOT NULL,\n"
                                + "   startDate DATETIME NOT NULL,\n"
                                + "   PRIMARY KEY (orderId),\n"
                                + "   FOREIGN KEY (orderId) REFERENCES Orders(id) ON DELETE CASCADE\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    public DeliveryInfo find(int orderId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setInt(1, orderId);

        return findOne(con, st);
    }

    private String getFindStatement() {
        return String.format("SELECT * FROM %s WHERE orderId = ?;", TABLE_NAME);
    }

    public void insert(DeliveryInfo deliveryInfo) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        st.setInt(1, deliveryInfo.getOrderId());
        st.setString(2, deliveryInfo.getDeliveryId());
        st.setDouble(3, deliveryInfo.getVelocity());
        st.setDouble(4, deliveryInfo.getLocation().getX());
        st.setDouble(5, deliveryInfo.getLocation().getY());
        st.setTimestamp(6, new java.sql.Timestamp(deliveryInfo.getStartDate().getTime()));

        executeUpdate(con, st);
    }

    private String getInsertStatement() {
        return String.format(
                "INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?);", TABLE_NAME, COLUMN_NAMES);
    }

    @Override
    public DeliveryInfo convertResultSetToObject(ResultSet rs) throws SQLException {
        int orderId = rs.getInt(1);
        String deliveryId = rs.getString(2);
        double velocity = rs.getDouble(3);
        double posX = rs.getDouble(4);
        double posY = rs.getDouble(5);
        Timestamp timestamp = rs.getTimestamp(6);
        java.util.Date startDate = new java.util.Date(timestamp.getTime());

        return new DeliveryInfo(orderId, deliveryId, velocity, new Location(posX, posY), startDate);
    }
}
