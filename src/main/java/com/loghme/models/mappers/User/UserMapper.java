package com.loghme.models.mappers.User;

import com.loghme.database.ConncetionPool.ConnectionPool;
import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static UserMapper instance = null;
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_NAMES =
            "id, firstName, lastName, phoneNumber, email, credit, posX, posY";

    public static UserMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    private UserMapper() throws SQLException {
        createTable();
    }

    @Override
    public ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();

        statements.add(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (\n"
                                + "   id INTEGER PRIMARY KEY,\n"
                                + "   firstName VARCHAR(1023) NOT NULL,\n"
                                + "   lastName VARCHAR(1023) NOT NULL,\n"
                                + "   phoneNumber VARCHAR(255) NOT NULL,\n"
                                + "   email VARCHAR(255) NOT NULL,\n"
                                + "   credit DOUBLE NOT NULL DEFAULT 0,\n"
                                + "   posX DOUBLE NOT NULL,\n"
                                + "   posY DOUBLE NOT NULL\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    @Override
    public User convertResultSetToObject(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);
        String phoneNumber = rs.getString(4);
        String email = rs.getString(5);
        double credit = rs.getDouble(6);
        Location location = new Location(rs.getDouble(7), rs.getDouble(8));
        return new User(id, firstName, lastName, phoneNumber, email, credit, location);
    }

    public User find(int userId) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setInt(1, userId);

        return findOne(con, st);
    }

    public void insert(User user) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());

        st.setInt(1, user.getId());
        st.setString(2, user.getFirstName());
        st.setString(3, user.getLastName());
        st.setString(4, user.getPhoneNumber());
        st.setString(5, user.getEmail());
        st.setDouble(6, user.getCredit());
        st.setDouble(7, user.getLocation().getX());
        st.setDouble(8, user.getLocation().getY());

        executeUpdate(con, st);
    }

    private String getFindStatement() {
        return String.format("SELECT * FROM %s WHERE id = ?;", TABLE_NAME);
    }

    private String getInsertStatement() {
        return String.format(
                "INSERT IGNORE INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                TABLE_NAME, COLUMN_NAMES);
    }

    public void updateCredit(int userId, double credit) throws SQLException {
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = con.prepareStatement(getUpdateCreditStatement());

        st.setDouble(1, credit);
        st.setInt(2, userId);

        executeUpdate(con, st);
    }

    private String getUpdateCreditStatement() {
        return String.format("UPDATE %s SET credit = ? WHERE id = ?;", TABLE_NAME);
    }
}
