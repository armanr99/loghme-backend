package com.loghme.exceptions;

public class WrongAmount extends Exception {
    public String toString() {
        return "Charge amount must be positive";
    }
}
