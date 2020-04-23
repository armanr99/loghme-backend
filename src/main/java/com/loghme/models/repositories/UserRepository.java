package com.loghme.models.repositories;

import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.User.User;
import com.loghme.models.domain.Wallet.Wallet;

public class UserRepository {
    private User user;
    private static UserRepository instance = null;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        user = getSampleUser();
    }

    public static void clearInstance() {
        instance = null;
    }

    public User getUser() {
        return user;
    }

    private User getSampleUser() {
        int id = 0;
        String firstName = "احسان";
        String lastName = "خامس‌پناه";
        String phoneNumber = "+989123456789";
        String email = "ekhamespanah@yahoo.com";
        Location location = new Location(0, 0);
        Wallet wallet = new Wallet(100000);
        return new User(id, firstName, lastName, phoneNumber, email, location, wallet);
    }
}