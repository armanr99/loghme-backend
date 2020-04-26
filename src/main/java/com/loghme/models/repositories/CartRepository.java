package com.loghme.models.repositories;

import com.loghme.exceptions.CartItemDoesntExist;
import com.loghme.exceptions.EmptyCart;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.mappers.CartItem.CartItemMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartRepository {
    private static CartRepository instance = null;

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    public ArrayList<CartItem> getCartItems(int userId) throws SQLException {
        return CartItemMapper.getInstance().findAll(userId);
    }

    public CartItem getCartItem(int userId, String restaurantId, String foodName)
            throws CartItemDoesntExist, SQLException {
        CartItem cartItem = CartItemMapper.getInstance().find(userId, restaurantId, foodName);

        if (cartItem == null) {
            throw new CartItemDoesntExist(foodName, restaurantId);
        } else {
            return cartItem;
        }
    }

    public void addCartItem(CartItem cartItem) throws SQLException {
        CartItemMapper.getInstance().insert(cartItem);
    }

    public void deleteCart(int userId) throws SQLException {
        CartItemMapper.getInstance().delete(userId);
    }

    public Restaurant getCartRestaurant(int userId)
            throws EmptyCart, RestaurantDoesntExist, SQLException {
        CartItem cartItem = CartItemMapper.getInstance().findFirst(userId);
        if(cartItem == null) {
            throw new EmptyCart();
        } else {
            String restaurantId = cartItem.getRestaurantId();
            return RestaurantRepository.getInstance().getRestaurant(restaurantId);
        }
    }

    public void deleteCartItem(int userId, String restaurantId, String foodName) throws SQLException {
        CartItemMapper.getInstance().delete(userId, restaurantId, foodName);
    }

    public void updateCount(int userId, String restaurantId, String foodName, int count) throws SQLException {
        CartItemMapper.getInstance().updateCount(userId, restaurantId, foodName, count);
    }
}
