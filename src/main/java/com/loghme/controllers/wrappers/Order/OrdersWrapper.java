package com.loghme.controllers.wrappers.Order;

import com.loghme.models.Order.Order;

import java.util.ArrayList;

public class OrdersWrapper {
    ArrayList<OrderWrapper> orders;

    public OrdersWrapper(ArrayList<Order> orders) {
        orders = new ArrayList<>();

        for(Order order : orders) {
            this.orders.add(new OrderWrapper(order));
        }
    }
}
