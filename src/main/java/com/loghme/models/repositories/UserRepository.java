package com.loghme.models.repositories;

import com.loghme.configs.UserConfigs;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.User.User;

import java.util.ArrayList;

public class UserRepository {
    private ArrayList<User> users;
    private static UserRepository instance = null;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        users = new ArrayList<>();
        users.add(getSampleUser());
    }

    public User getUser(int userId) throws UserDoesntExist {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }

        throw new UserDoesntExist(userId);
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
