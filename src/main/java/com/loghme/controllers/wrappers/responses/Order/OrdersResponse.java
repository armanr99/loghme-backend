package com.loghme.controllers.wrappers.responses.Order;

import com.loghme.models.domain.Order.Order;

import java.util.ArrayList;

public class OrdersResponse {
    ArrayList<OrderResponse> orders;

    public OrdersResponse(ArrayList<Order> orders) {
        this.orders = new ArrayList<>();

        for(Order order : orders) {
            this.orders.add(new OrderResponse(order));
        }
    }
}
