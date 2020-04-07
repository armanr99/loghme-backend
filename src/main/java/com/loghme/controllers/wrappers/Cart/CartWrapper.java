package com.loghme.controllers.wrappers.Cart;

import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;

import java.util.ArrayList;

public class CartWrapper {
    ArrayList<CartItemWrapper> items;

    public CartWrapper(Cart cart) {
        items = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItemsList()) {
            items.add(new CartItemWrapper(cartItem));
        }
    }
}
