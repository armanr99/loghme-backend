package com.loghme.exceptions;

public class OrderItemDoesntExist extends Exception {
    public String toString() {
        return "There is no OrderItem related to selected Order";
    }
}
