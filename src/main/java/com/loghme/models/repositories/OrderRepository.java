package com.loghme.models.repositories;

import com.loghme.exceptions.OrderDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.OrderItem.OrderItem;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.mappers.Order.OrderMapper;
import com.loghme.models.mappers.OrderItem.OrderItemMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepository {
    private static OrderRepository instance = null;

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public ArrayList<Order> getOrders(int userId) throws SQLException {
        return OrderMapper.getInstance().findAll(userId);
    }

    public Order getOrder(int orderId) throws OrderDoesntExist, SQLException {
        Order order = OrderMapper.getInstance().find(orderId);

        if (order == null) {
            throw new OrderDoesntExist(orderId);
        } else {
            return order;
        }
    }

    public void addAndSetOrder(Order order) throws SQLException {
        OrderMapper.getInstance().insert(order);
    }

    public void addOrderItems(ArrayList<OrderItem> orderItems) throws SQLException {
        OrderItemMapper.getInstance().insertBatch(orderItems);
    }

    public ArrayList<OrderItem> getOrderItems(int orderId) throws SQLException {
        return OrderItemMapper.getInstance().findAll(orderId);
    }

    public Restaurant getOrderRestaurant(int orderId)
            throws OrderItemDoesntExist, RestaurantDoesntExist, SQLException {
        OrderItem orderItem = OrderItemMapper.getInstance().findFirst(orderId);

        if (orderItem == null) {
            throw new OrderItemDoesntExist();
        } else {
            String restaurantId = orderItem.getRestaurantId();
            return RestaurantRepository.getInstance().getRestaurant(restaurantId);
        }
    }
}
