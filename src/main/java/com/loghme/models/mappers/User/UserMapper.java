package com.loghme.models.mappers.User;

import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static UserMapper instance = null;
    private static final String TABLE_NAME = "User";

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
                                + "   email VARCHAR(255) NOT NULL,\n"
                                + "   phoneNumber VARCHAR(255) NOT NULL,\n"
                                + "   credit DOUBLE NOT NULL DEFAULT 0,\n"
                                + "   posX DOUBLE NOT NULL\n"
                                + "   posY DOUBLE NOT NULL\n"
                                + ");",
                        TABLE_NAME));
        return statements;
    }

    @Override
    public String getFindStatement(Integer id) {
        return "";
    }

    @Override
    public String getInsertStatement(User user) {
        return "";
    }

    @Override
    public String getDeleteStatement(Integer id) {
        return "";
    }

    @Override
    public User convertResultSetToObject(ResultSet rs) throws SQLException {
        return null;
    }
}
