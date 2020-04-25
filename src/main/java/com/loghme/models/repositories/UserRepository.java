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

    public void addSampleUser() throws SQLException {
        User sampleUser = getSampleUser();
        UserMapper.getInstance().insert(sampleUser);
    }

    public User getUser(int userId) throws UserDoesntExist, SQLException {
        User user = UserMapper.getInstance().find(userId);

        if (user == null) {
            throw new UserDoesntExist(userId);
        } else {
            return user;
        }
    }

    private User getSampleUser() {
        int id = UserConfigs.DEFAULT_ID;
        String firstName = "احسان";
        String lastName = "خامس‌پناه";
        String phoneNumber = "+989123456789";
        String email = "ekhamespanah@yahoo.com";
        double credit = 100000;
        Location location = new Location(0, 0);
        return new User(id, firstName, lastName, phoneNumber, email, credit, location);
    }

    public void updateUser(User user) {}
}
