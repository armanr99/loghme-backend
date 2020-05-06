package com.loghme.controllers.DTOs.responses.Order;

import com.loghme.exceptions.*;
import com.loghme.models.domain.Order.Order;

import java.sql.SQLException;

public class OrderResponse {
    private int id;
    private OrderItemsResponse cart;
    private String state;

    public OrderResponse(Order order)
            throws FoodDoesntExist, RestaurantDoesntExist, OrderItemDoesntExist, SQLException,
                    UserDoesntExist, OrderDoesntExist {
        this.id = order.getId();
        this.cart = new OrderItemsResponse(order);
        this.state = order.getState();
    }
}
