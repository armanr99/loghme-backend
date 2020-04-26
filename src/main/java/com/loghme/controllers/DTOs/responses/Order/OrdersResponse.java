package com.loghme.controllers.DTOs.responses.Order;

import com.loghme.exceptions.*;
import com.loghme.models.domain.Order.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersResponse {
    private ArrayList<OrderResponse> orders;

    public OrdersResponse(ArrayList<Order> orders) throws RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist, SQLException, UserDoesntExist, OrderDoesntExist {
        this.orders = new ArrayList<>();

        for(Order order : orders) {
            this.orders.add(new OrderResponse(order));
        }
    }
}
