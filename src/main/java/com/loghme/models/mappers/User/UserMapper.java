package com.loghme.models.mappers.User;

import com.loghme.database.Mapper.Mapper;
import com.loghme.models.domain.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static UserMapper instance = null;

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
        return null;
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
