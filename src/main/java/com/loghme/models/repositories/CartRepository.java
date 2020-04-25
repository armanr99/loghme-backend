package com.loghme.models.repositories;

import com.loghme.exceptions.CartItemDoesntExist;
import com.loghme.exceptions.EmptyCart;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class CartRepository {
    private ArrayList<CartItem> cartItems;
    private static CartRepository instance = null;

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    private CartRepository() {
        cartItems = new ArrayList<>();
    }

    public ArrayList<CartItem> getCartItems(int userId) {
        ArrayList<CartItem> userCartItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            if (userId == cartItem.getUserId()) {
                userCartItems.add(cartItem);
            }
        }

        return userCartItems;
    }

    public CartItem getCartItem(int userId, String restaurantId, String foodName)
            throws CartItemDoesntExist {
        for (CartItem cartItem : cartItems) {
            if (userId == cartItem.getUserId()
                    && restaurantId.equals(cartItem.getRestaurantId())
                    && foodName.equals(cartItem.getFoodName())) {
                return cartItem;
            }
        }

        throw new CartItemDoesntExist(foodName, restaurantId);
    }

    public void updateCartItem(CartItem cartItem) {}

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void deleteCart(int userId) {
        cartItems.clear();
    }

    public Restaurant getCartRestaurant(int userId) throws EmptyCart, RestaurantDoesntExist, SQLException {
        CartItem orderItem = getFirstCartItem(userId);
        String restaurantId = orderItem.getRestaurantId();

        return RestaurantRepository.getInstance().getRestaurant(restaurantId);
    }

    private CartItem getFirstCartItem(int userId) throws EmptyCart {
        for (CartItem cartItem : cartItems) {
            if (userId == cartItem.getUserId()) {
                return cartItem;
            }
        }

        throw new EmptyCart();
    }

    public void deleteCartItem(int userId, String restaurantId, String foodName) {
        Iterator<CartItem> it = cartItems.iterator();
        while (it.hasNext()) {
            CartItem cartItem = it.next();
            if (userId == cartItem.getUserId()
                    && restaurantId.equals(cartItem.getRestaurantId())
                    && foodName.equals(cartItem.getFoodName())) {
                it.remove();
                break;
            }
        }
    }
}
