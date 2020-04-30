package com.loghme.exceptions;

public class UserNotAuthorized extends Exception {
    public String toString() {
        return "User is not authorized";
    }
}
