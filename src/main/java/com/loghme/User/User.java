package com.loghme.User;

import com.loghme.Cart.Cart;
import com.loghme.Cart.DifferentRestaurant;
import com.loghme.Food.Food;
import com.loghme.Location.Location;
import com.loghme.Restaurant.Restaurant;

public class User {
    private Cart cart;
    private Location location;

    public User() {
        cart = new Cart();
        location = new Location(0, 0);
    }

    public void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant {
        cart.addToCart(food, restaurant);
    }
}
