package com.loghme.models.repositories;

import com.loghme.configs.UserConfigs;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.User.User;
import com.loghme.models.mappers.User.UserMapper;

import java.sql.SQLException;

public class UserRepository {
    private static UserRepository instance = null;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }


    public User getUser(int userId) throws UserDoesntExist, SQLException {
        User user = UserMapper.getInstance().find(userId);

        if (user == null) {
            throw new UserDoesntExist(userId);
        } else {
            return user;
        }
    }

    public void updateCredit(int userId, double credit) throws SQLException {
        UserMapper.getInstance().updateCredit(userId, credit);
    }
}
