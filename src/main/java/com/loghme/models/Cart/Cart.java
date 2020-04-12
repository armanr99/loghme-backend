package com.loghme.models.Cart;

import com.loghme.models.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.exceptions.InvalidCount;
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

    public Order finalizeOrder() throws EmptyCartFinalize, InvalidCount {
        if(cartItems.size() == 0)
            throw new EmptyCartFinalize();
        else {
            finalizeItems();
            return new Order(this);
        }
    }

    private void finalizeItems() throws InvalidCount {
        for(CartItem cartItem : cartItems.values())
            cartItem.finalizeItem();
    }

    private void handleRestaurant(Restaurant restaurant) throws DifferentRestaurant {
        if(this.restaurant == null)
            this.restaurant = restaurant;
        else if(!this.restaurant.getId().equals(restaurant.getId()))
            throw new DifferentRestaurant(this.restaurant.getId());
    }

    private void handleAddCartItem(Food food, Restaurant restaurant) throws InvalidCount {
        try {
            validateCount(food);
            addCartItem(food, restaurant);
        } catch(InvalidCount invalidCount) {
            if(cartItems.size() == 0)
                this.restaurant = null;
            throw invalidCount;
        }
    }

    private void validateCount(Food food) throws InvalidCount {
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

    public void removeItem(String foodName, String restaurantId) throws CartItemDoesntExist {
        validateRemoveItem(foodName, restaurantId);
        removeItemFromCart(foodName, restaurantId);
    }

    private void validateRemoveItem(String foodName, String restaurantId) throws CartItemDoesntExist {
        if(cartItems.size() == 0)
            throw new CartItemDoesntExist(foodName, restaurantId);
        else if(!restaurant.getId().equals(restaurantId))
            throw new CartItemDoesntExist(foodName, restaurantId);
        else if(!cartItems.containsKey(foodName))
            throw new CartItemDoesntExist(foodName, restaurantId);
    }

    private void removeItemFromCart(String foodName, String restaurantId) {
        CartItem cartItem = cartItems.get(foodName);

        if(cartItem.getCount() == 1) {
            cartItems.remove(foodName);
            this.restaurant = null;
        }
        else {
            cartItem.decreaseCount();
        }
    }
}
