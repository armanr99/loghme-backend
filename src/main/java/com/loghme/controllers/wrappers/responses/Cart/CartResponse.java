package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.CartItem.CartItem;

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
