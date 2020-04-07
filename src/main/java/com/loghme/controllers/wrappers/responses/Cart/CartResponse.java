package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;

import java.util.ArrayList;

public class CartResponse {
    ArrayList<CartItemResponse> items;

    public CartResponse(Cart cart) {
        items = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItemsList()) {
            items.add(new CartItemResponse(cartItem));
        }
    }
}
