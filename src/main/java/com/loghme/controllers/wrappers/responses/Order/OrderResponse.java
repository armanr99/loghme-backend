package com.loghme.controllers.wrappers.responses.Order;

import com.loghme.controllers.wrappers.responses.Cart.CartItemResponse;
import com.loghme.controllers.wrappers.responses.Cart.CartResponse;
import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Order.Order;

import java.util.ArrayList;

public class OrderResponse {
    private String id;
    private ArrayList<CartItemResponse> cart;
    private String state;

    public OrderResponse(Order order) {
        this.id = order.getId();
        addCart(order.getCart());
        this.state = order.getDeliveryInfo().getState();
    }

    private void addCart(Cart cart) {
        this.cart = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItemsList()) {
            this.cart.add(new CartItemResponse(cartItem));
        }
    }
}