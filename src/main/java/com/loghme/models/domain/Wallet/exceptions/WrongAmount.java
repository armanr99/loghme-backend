package com.loghme.models.domain.Wallet.exceptions;

public class WrongAmount extends Exception {
    public String toString() {
        return "Charge amount must be positive";
    }
}
