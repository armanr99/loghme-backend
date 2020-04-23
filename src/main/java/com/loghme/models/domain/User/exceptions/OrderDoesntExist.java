package com.loghme.models.domain.User.exceptions;


public class OrderDoesntExist extends Exception {
    private int inavlidOrderId;

    public OrderDoesntExist(int inavlidOrderId) {
        this.inavlidOrderId = inavlidOrderId;
    }

    public String toString() {
        return String.format("Order with id %d does not exist", inavlidOrderId);
    }
}