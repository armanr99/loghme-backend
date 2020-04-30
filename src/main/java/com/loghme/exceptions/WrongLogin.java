package com.loghme.exceptions;

public class WrongLogin extends Exception {
    public String toString() {
        return "Wrong username or password";
    }
}
