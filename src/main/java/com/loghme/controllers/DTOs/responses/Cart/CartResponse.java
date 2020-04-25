package com.loghme.controllers.DTOs.responses.Cart;

import com.loghme.exceptions.EmptyCart;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.util.ArrayList;

public class CartResponse {
    private ArrayList<CartItemResponse> items;
    private double totalPrice;

    public CartResponse(Cart cart) throws FoodDoesntExist, RestaurantDoesntExist {
        totalPrice = cart.getTotalPrice();
        setItems(cart);
    }

    private void setItems(Cart cart) throws RestaurantDoesntExist, FoodDoesntExist {
        this.items = new ArrayList<>();
        Restaurant restaurant;

        try {
            restaurant = cart.getRestaurant();
        } catch (EmptyCart emptyCart) {
            restaurant = null;
        }

        for (CartItem cartItem : cart.getCartItems()) {
            this.items.add(new CartItemResponse(restaurant, cartItem));
        }
    }
}
