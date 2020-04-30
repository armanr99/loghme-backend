package com.loghme.exceptions;

public class UserNotAuthenticated extends Exception {
    public String toString() {
        return "User is not authenticated";
    }
}
