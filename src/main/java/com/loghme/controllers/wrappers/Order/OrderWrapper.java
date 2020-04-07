package com.loghme.controllers.wrappers.Order;

import com.loghme.controllers.wrappers.Cart.CartWrapper;
import com.loghme.models.Order.Order;

public class OrderWrapper {
    private String id;
    private CartWrapper cart;
    private DeliveryInfoWrapper deliveryInfo;

    public OrderWrapper(Order order) {
        this.id = order.getId();
        this.cart = new CartWrapper(order.getCart());
        this.deliveryInfo = new DeliveryInfoWrapper(order.getDeliveryInfo());
    }
}