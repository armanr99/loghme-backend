package com.loghme.exceptions;

public class EmailAlreadyExists extends Exception {
    private String email;

    public EmailAlreadyExists(String email) {
        this.email = email;
    }

    public String toString() {
        return String.format("User with email %s already exists", email);
    }
}
