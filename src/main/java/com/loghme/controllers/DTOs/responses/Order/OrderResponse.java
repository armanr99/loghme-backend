package com.loghme.controllers.DTOs.responses.Order;

import com.loghme.controllers.DTOs.responses.Cart.CartResponse;;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.OrderItem.OrderItem;

import java.util.ArrayList;


public class OrderResponse {
    private int id;
    private OrderItemsResponse cart;
    private String state;

    public OrderResponse(Order order) throws FoodDoesntExist, RestaurantDoesntExist, OrderItemDoesntExist {
        this.id = order.getId();
        this.cart = new OrderItemsResponse(order);
        this.state = order.getState();
    }
}