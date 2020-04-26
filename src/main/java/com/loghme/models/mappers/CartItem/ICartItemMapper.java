package com.loghme.models.mappers.CartItem;

import com.loghme.models.domain.CartItem.CartItem;

import java.sql.SQLException;
import java.util.ArrayList;

interface ICartItemMapper {
    CartItem find(int userId, String restaurantId, String foodName) throws SQLException;
    ArrayList<CartItem> findAll(int userId) throws SQLException;
    void insert(CartItem cartItem) throws SQLException;
    void delete(int userId, String restaurantId, String foodName) throws SQLException;
    void updateCount(int userId, String restaurantId, String foodName, int count) throws SQLException;
    CartItem findFirst(int userId) throws SQLException;
    void delete(int userId) throws SQLException;
}
