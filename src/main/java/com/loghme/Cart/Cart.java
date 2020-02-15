package com.loghme.Cart;

import com.loghme.Cart.Exceptions.DifferentRestaurant;
import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.CartItem.CartItem;
import com.loghme.Food.Food;
import com.loghme.Restaurant.Restaurant;

import java.util.*;

public class Cart {
    private Restaurant restaurant;
    private Map<String, CartItem> cartItems;

    public Cart() {
        cartItems = new HashMap<>();
    }

    public void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant {
        handleRestaurant(restaurant);
        handleAddCartItem(food, restaurant);
    }

    public ArrayList<CartItem> getCartItemsList() {
        return new ArrayList<>(cartItems.values());
    }

    public void finalizeOrder() throws EmptyCartFinalize {
        if(cartItems.size() == 0)
            throw new EmptyCartFinalize();
        else
            cartItems = Collections.emptyMap();
    }

    private void handleRestaurant(Restaurant restaurant) throws DifferentRestaurant {
        if(this.restaurant == null)
            this.restaurant = restaurant;
        else if(!this.restaurant.getID().equals(restaurant.getID()))
            throw new DifferentRestaurant(this.restaurant.getID());
    }

    private void handleAddCartItem(Food food, Restaurant restaurant) {
        if(cartItems.containsKey(food.getName())) {
            cartItems.get(food.getName()).increaseCount();
        } else {
            CartItem newCartItem = new CartItem(food, restaurant);
            cartItems.put(food.getName(), newCartItem);
        }
    }
}
