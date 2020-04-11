package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;

import java.util.ArrayList;

public class CartResponse {
    ArrayList<CartItemResponse> cart;

    public CartResponse(Cart cart) {
        this.cart = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItemsList()) {
            this.cart.add(new CartItemResponse(cartItem));
        }
    }
}
