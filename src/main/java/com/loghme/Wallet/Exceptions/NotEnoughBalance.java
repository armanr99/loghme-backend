package com.loghme.Wallet.Exceptions;

public class NotEnoughBalance extends Throwable {
    public String toString() {
        return "There is not enough balance in wallet";
    }
}
