package com.loghme.models.domain.Cart.exceptions;

public class EmptyCartFinalize extends Exception {
    public String toString() {
        return "Cart is empty";
    }
}
