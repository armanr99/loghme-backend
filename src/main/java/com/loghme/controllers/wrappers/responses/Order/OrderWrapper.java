package com.loghme.controllers.wrappers.responses.Order;

import com.loghme.controllers.wrappers.responses.Cart.CartWrapper;
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