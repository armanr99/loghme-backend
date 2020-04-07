package com.loghme.controllers.wrappers.Cart;

import com.loghme.controllers.wrappers.Restaurant.RestaurantWrapper;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Food;

class CartItemWrapper {
    private Food food;
    private RestaurantWrapper restaurant;
    private int count;

    CartItemWrapper(CartItem cartItem) {
        this.food = cartItem.getFood();
        this.restaurant = new RestaurantWrapper(cartItem.getRestaurant());
        this.count = cartItem.getCount();
    }
}
