package com.loghme.models.Cart;

import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Exceptions.InvalidCount;
import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Order.Order;
import com.loghme.models.Restaurant.Restaurant;

import java.util.*;

public class Cart {
    private Restaurant restaurant;
    private Map<String, CartItem> cartItems;

    public Cart() {
        cartItems = new HashMap<>();
    }

    public void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant, InvalidCount {
        handleRestaurant(restaurant);
        handleAddCartItem(food, restaurant);
    }

    public ArrayList<CartItem> getCartItemsList() {
        return new ArrayList<>(cartItems.values());
    }

    public Order finalizeOrder() throws EmptyCartFinalize {
        if(cartItems.size() == 0)
            throw new EmptyCartFinalize();
        else
            return new Order(this);
    }

    private void handleRestaurant(Restaurant restaurant) throws DifferentRestaurant {
        if(this.restaurant == null)
            this.restaurant = restaurant;
        else if(!this.restaurant.getId().equals(restaurant.getId()))
            throw new DifferentRestaurant(this.restaurant.getId());
    }

    private void handleAddCartItem(Food food, Restaurant restaurant) throws InvalidCount {

        vaildateCount(food);
        addCartItem(food, restaurant);
    }

    private void vaildateCount(Food food) throws InvalidCount {
        int newCount = 1;

        if(cartItems.containsKey(food.getName()))
            newCount += cartItems.get(food.getName()).getCount();

        food.validateCount(newCount);
    }

    private void addCartItem(Food food, Restaurant restaurant) {
        if (cartItems.containsKey(food.getName())) {
            cartItems.get(food.getName()).increaseCount();
        } else {
            CartItem newCartItem = new CartItem(food, restaurant);
            cartItems.put(food.getName(), newCartItem);
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;

        for(CartItem cartItem : cartItems.values())
            totalPrice += cartItem.getTotalPrice();

        return totalPrice;
    }

    public String getRestaurantName() {
        return (restaurant == null ? null : restaurant.getName());
    }

    public Location getRestaurantLocation() {
        return (restaurant == null ? null : restaurant.getLocation());
    }
}
