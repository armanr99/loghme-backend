package com.loghme.controllers.DTOs.responses.Order;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Order.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersResponse {
    private ArrayList<OrderResponse> orders;

    public OrdersResponse(ArrayList<Order> orders) throws RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist, SQLException {
        this.orders = new ArrayList<>();

        for(Order order : orders) {
            this.orders.add(new OrderResponse(order));
        }
    }
}
