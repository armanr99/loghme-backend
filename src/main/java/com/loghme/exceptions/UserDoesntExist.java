package com.loghme.exceptions;

public class UserDoesntExist extends Exception {
    private int userId;

    public UserDoesntExist(int userId) {
        this.userId = userId;
    }

    public String toString() {
        return String.format("User with id %d does not exist", userId);
    }
}
