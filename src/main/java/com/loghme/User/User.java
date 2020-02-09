package com.loghme.User;

import com.loghme.Cart.Cart;
import com.loghme.Cart.DifferentRestaurant;
import com.loghme.CartItem.CartItem;
import com.loghme.Food.Food;
import com.loghme.Location.Location;
import com.loghme.Restaurant.Restaurant;

import java.util.ArrayList;

public class User {
    private Cart cart;
    private Location location;

    public User() {
        cart = new Cart();
        location = new Location(0, 0);
    }

    public Location getLocation() {
        return location;
    }

    public void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant {
        cart.addToCart(food, restaurant);
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cart.getCartItemsList();
    }

    public void finalizeOrder() {
        cart.finalizeOrder();
    }
}