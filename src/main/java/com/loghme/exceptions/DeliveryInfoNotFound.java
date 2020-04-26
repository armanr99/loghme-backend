package com.loghme.exceptions;

public class DeliveryInfoNotFound extends Exception {
    private int orderId;

    public DeliveryInfoNotFound(int orderId) {
        this.orderId = orderId;
    }

    public String toString() {
        return String.format("DeliverInfo not found for order with id %d", orderId);
    }
}
