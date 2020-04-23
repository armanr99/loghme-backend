package com.loghme.controllers.wrappers.responses.Order;

import com.loghme.controllers.wrappers.responses.Cart.CartResponse;;
import com.loghme.models.domain.Order.Order;


public class OrderResponse {
    private int id;
    private CartResponse cart;
    private String state;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.cart = new CartResponse(order.getCart());
        this.state = order.getState();
    }
}