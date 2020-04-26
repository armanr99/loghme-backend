package com.loghme.models.domain.Order;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.exceptions.*;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.DeliveryInfo.DeliveryInfo;
import com.loghme.models.domain.OrderItem.OrderItem;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.DeliveryInfoRepository;
import com.loghme.models.repositories.OrderRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
    private int id;
    private int userId;

    public Order(int userId) {
        this.userId = userId;
    }

    public Order(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addOrderItems(ArrayList<CartItem> cartItems) throws SQLException {
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem =
                    new OrderItem(
                            this.id,
                            cartItem.getRestaurantId(),
                            cartItem.getFoodName(),
                            cartItem.getCount());
            orderItems.add(orderItem);
        }

        OrderRepository.getInstance().addOrderItems(orderItems);
    }

    public String getState() throws OrderItemDoesntExist, SQLException, UserDoesntExist, RestaurantDoesntExist, OrderDoesntExist {
        try {
            DeliveryInfo deliveryInfo =
                    DeliveryInfoRepository.getInstance().getDeliveryInfo(this.id);
            return deliveryInfo.getState();
        } catch (DeliveryInfoNotFound deliveryInfoNotFound) {
            return DeliveryConfigs.State.SEARCHING;
        }
    }

    public void setDelivery(DeliveryInfo deliveryInfo) throws SQLException {
        DeliveryInfoRepository.getInstance().addDeliveryInfo(deliveryInfo);
    }

    public ArrayList<OrderItem> getOrderItems() throws SQLException {
        return OrderRepository.getInstance().getOrderItems(id);
    }

    public Restaurant getRestaurant()
            throws RestaurantDoesntExist, OrderItemDoesntExist, SQLException {
        return OrderRepository.getInstance().getOrderRestaurant(this.id);
    }

    public double getTotalPrice() throws FoodDoesntExist, SQLException {
        double totalPrice = 0;

        for (OrderItem orderItem : getOrderItems()) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}
