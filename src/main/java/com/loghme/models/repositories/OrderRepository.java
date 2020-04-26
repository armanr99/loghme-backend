package com.loghme.models.repositories;

import com.loghme.exceptions.OrderDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Order.OrderIdHandler;
import com.loghme.models.domain.OrderItem.OrderItem;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.mappers.Order.OrderMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepository {
    private ArrayList<OrderItem> orderItems;
    private static OrderRepository instance = null;

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    private OrderRepository() {
        orderItems = new ArrayList<>();
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

    public void addOrderItems(ArrayList<OrderItem> newOrderItems) {
        orderItems.addAll(newOrderItems);
    }

    public ArrayList<OrderItem> getOrderItems(int orderId) {
        ArrayList<OrderItem> outOrderItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            if (orderId == orderItem.getOrderId()) {
                outOrderItems.add(orderItem);
            }
        }

        return outOrderItems;
    }

    public Restaurant getOrderRestaurant(int orderId)
            throws OrderItemDoesntExist, RestaurantDoesntExist, SQLException {
        OrderItem orderItem = getFirstOrderItem(orderId);
        String restaurantId = orderItem.getRestaurantId();

        return RestaurantRepository.getInstance().getRestaurant(restaurantId);
    }

    private OrderItem getFirstOrderItem(int orderId) throws OrderItemDoesntExist {
        for (OrderItem orderItem : orderItems) {
            if (orderId == orderItem.getOrderId()) {
                return orderItem;
            }
        }

        throw new OrderItemDoesntExist();
    }
}
