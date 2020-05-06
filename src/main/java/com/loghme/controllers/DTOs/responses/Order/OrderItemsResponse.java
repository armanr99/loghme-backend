package com.loghme.controllers.DTOs.responses.Order;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.OrderItem.OrderItem;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

class OrderItemsResponse {
    private ArrayList<OrderItemResponse> items;
    private double totalPrice;

    OrderItemsResponse(Order order)
            throws FoodDoesntExist, RestaurantDoesntExist, OrderItemDoesntExist, SQLException {
        totalPrice = order.getTotalPrice();
        setItems(order);
    }

    private void setItems(Order order)
            throws RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist, SQLException {
        this.items = new ArrayList<>();
        Restaurant restaurant = order.getRestaurant();

        for (OrderItem orderItem : order.getOrderItems()) {
            this.items.add(new OrderItemResponse(restaurant, orderItem));
        }
    }
}
