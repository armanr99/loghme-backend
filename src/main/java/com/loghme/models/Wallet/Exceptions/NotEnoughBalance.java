package com.loghme.models.Wallet.Exceptions;

public class NotEnoughBalance extends Exception {
    public String toString() {
        return "There is not enough balance in wallet";
    }
}
