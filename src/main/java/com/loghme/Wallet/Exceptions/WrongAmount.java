package com.loghme.Wallet.Exceptions;

public class WrongAmount extends Exception {
    public String toString() {
        return "Charge amount must be positive";
    }
}
