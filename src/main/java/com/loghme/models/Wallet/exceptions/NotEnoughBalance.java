package com.loghme.models.Wallet.exceptions;

public class NotEnoughBalance extends Exception {
    public String toString() {
        return "There is not enough balance in wallet";
    }
}
