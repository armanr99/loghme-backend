package com.loghme.exceptions;

public class NotEnoughBalance extends Exception {
    public String toString() {
        return "There is not enough balance in wallet";
    }
}
