package com.loghme.models.mappers.User;

import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static UserMapper instance = null;
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_NAMES =
            " id, firstName, lastName, phoneNumber, email, credit, posX, posY ";

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

        statements.add(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        statements.add(
                String.format(
                        "CREATE TABLE %s (\n"
                                + "   id INTEGER PRIMARY KEY\n"
                                + "   firstName VARCHAR(255) NOT NULL,\n"
                                + "   lastName VARCHAR(255) NOT NULL,\n"
                                + "   phoneNumber VARCHAR(255) NOT NULL,\n"
                                + "   email VARCHAR(255) NOT NULL,\n"
                                + "   credit DOUBLE NOT NULL DEFAULT 0,\n"
                                + "   posX DOUBLE NOT NULL\n"
                                + "   posY DOUBLE NOT NULL\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    @Override
    public String getFindStatement(Integer id) {
        return String.format("SELECT * FROM %s WHERE id = %d;", TABLE_NAME, id);
    }

    @Override
    public String getInsertStatement(User user) {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%d, %s, %s, %s, %s, %f, %f, %f",
                TABLE_NAME,
                COLUMN_NAMES,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getCredit(),
                user.getLocation().getX(),
                user.getLocation().getY());
    }

    @Override
    public String getDeleteStatement(Integer id) {
        return String.format("DELETE FROM %s WHERE id = %d;", TABLE_NAME, id);
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
}
