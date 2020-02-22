package com.loghme.models.User.Exceptions;


public class OrderDoesntExist extends Exception {
    private String inavlidOrderId;

    public OrderDoesntExist(String inavlidOrderId) {
        this.inavlidOrderId = inavlidOrderId;
    }

    public String toString() {
        return String.format("Order with id %s does not exist", inavlidOrderId);
    }
}