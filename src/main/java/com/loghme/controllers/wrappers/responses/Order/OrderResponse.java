package com.loghme.controllers.wrappers.responses.Order;

import com.loghme.controllers.wrappers.responses.Cart.CartResponse;
import com.loghme.models.Order.Order;

public class OrderResponse {
    private String id;
    private CartResponse cart;
    private DeliveryInfoResponse deliveryInfo;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.cart = new CartResponse(order.getCart());
        this.deliveryInfo = new DeliveryInfoResponse(order.getDeliveryInfo());
    }
}