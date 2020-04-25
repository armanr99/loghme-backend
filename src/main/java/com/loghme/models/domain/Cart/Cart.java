package com.loghme.models.domain.Cart;

import com.loghme.exceptions.*;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.CartRepository;
import com.loghme.models.repositories.OrderRepository;
import com.loghme.models.services.DeliveryService;

import java.sql.SQLException;
import java.util.*;

public class Cart {
    private int userId;

    public Cart(int userId) {
        this.userId = userId;
    }

    public ArrayList<CartItem> getCartItems() {
        return CartRepository.getInstance().getCartItems(userId);
    }

    public void addItem(String restaurantId, String foodName) throws DifferentRestaurant {
        validateSameRestaurant(restaurantId);
        handleAddItem(restaurantId, foodName);
    }

    public int getCartItemCount(String restaurantId, String foodName) {
        try {
            CartItem cartItem =
                    CartRepository.getInstance().getCartItem(userId, restaurantId, foodName);
            return cartItem.getCount();
        } catch (CartItemDoesntExist cartItemDoesntExist) {
            return 0;
        }
    }

    private void validateSameRestaurant(String restaurantId) throws DifferentRestaurant {
        ArrayList<CartItem> cartItems = getCartItems();

        if (cartItems.size() > 0) {
            if (!restaurantId.equals(cartItems.get(0).getRestaurantId())) {
                throw new DifferentRestaurant(restaurantId);
            }
        }
    }

    private void handleAddItem(String restaurantId, String foodName) {
        try {
            CartItem cartItem =
                    CartRepository.getInstance().getCartItem(userId, restaurantId, foodName);
            cartItem.increaseCount();
        } catch (CartItemDoesntExist cartItemDoesntExist) {
            CartRepository.getInstance().addCartItem(new CartItem(userId, restaurantId, foodName));
        }
    }

    public void removeItem(String restaurantId, String foodName) throws CartItemDoesntExist {
        CartItem cartItem =
                CartRepository.getInstance().getCartItem(userId, restaurantId, foodName);
        cartItem.decreaseCount();
    }

    public double getTotalPrice() throws FoodDoesntExist {
        double totalPrice = 0;

        for (CartItem cartItem : getCartItems()) {
            totalPrice += cartItem.getTotalPrice();
        }

        return totalPrice;
    }

    public void finalizeOrder()
            throws EmptyCart, InvalidCount, RestaurantDoesntExist, FoodDoesntExist, SQLException {
        ArrayList<CartItem> cartItems = getCartItems();

        validateFinalizeOrder(cartItems);
        runFinalizeOrder(cartItems);
    }

    private void validateFinalizeOrder(ArrayList<CartItem> cartItems) throws EmptyCart {
        if (cartItems.size() == 0) {
            throw new EmptyCart();
        }
    }

    private void runFinalizeOrder(ArrayList<CartItem> cartItems)
            throws RestaurantDoesntExist, FoodDoesntExist, InvalidCount, SQLException {
        try {
            finalizeItems(cartItems);
            Order order = createOrder(cartItems);
            CartRepository.getInstance().deleteCart(userId);
            DeliveryService.getInstance().addDelivery(order);
        } catch (InvalidCount | RestaurantDoesntExist | FoodDoesntExist ex) {
            CartRepository.getInstance().deleteCart(userId);
            throw ex;
        }
    }

    private void finalizeItems(ArrayList<CartItem> cartItems)
            throws InvalidCount, RestaurantDoesntExist, FoodDoesntExist, SQLException {
        for (CartItem cartItem : cartItems) {
            cartItem.finalizeItem();
        }
    }

    private Order createOrder(ArrayList<CartItem> cartItems) {
        Order order = new Order(userId);
        OrderRepository.getInstance().addAndSetOrder(order);
        order.addOrderItems(cartItems);

        return order;
    }

    public Restaurant getRestaurant() throws RestaurantDoesntExist, EmptyCart, SQLException {
        return CartRepository.getInstance().getCartRestaurant(userId);
    }
}
