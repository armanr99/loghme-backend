package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantResponse;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Food;

public class CartItemResponse {
    private Food food;
    private RestaurantResponse restaurant;
    private int count;

    public CartItemResponse(CartItem cartItem) {
        this.food = cartItem.getFood();
        this.restaurant = new RestaurantResponse(cartItem.getRestaurant());
        this.count = cartItem.getCount();
    }
}
