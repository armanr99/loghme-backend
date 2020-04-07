package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantResponse;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Food;

class CartItemResponse {
    private Food food;
    private RestaurantResponse restaurant;
    private int count;

    CartItemResponse(CartItem cartItem) {
        this.food = cartItem.getFood();
        this.restaurant = new RestaurantResponse(cartItem.getRestaurant());
        this.count = cartItem.getCount();
    }
}
