package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;

import java.util.ArrayList;

public class CartResponse {
    ArrayList<CartItemResponse> items;
    double totalPrice;

    public CartResponse(Cart cart) {
        totalPrice = cart.getTotalPrice();
        setItems(cart);
    }

    private void setItems(Cart cart) {
        this.items = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItemsList()) {
            this.items.add(new CartItemResponse(cartItem));
        }
    }
}
