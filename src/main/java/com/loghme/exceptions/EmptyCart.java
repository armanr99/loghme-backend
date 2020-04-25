package com.loghme.exceptions;

public class EmptyCart extends Exception {
    public String toString() {
        return "Cart is empty";
    }
}
