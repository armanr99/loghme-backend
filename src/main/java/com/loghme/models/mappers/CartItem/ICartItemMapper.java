package com.loghme.models.mappers.CartItem;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.utils.TripleKey.TripleKey;

import java.sql.SQLException;
import java.util.ArrayList;

interface ICartItemMapper extends IMapper<CartItem, TripleKey> {
    CartItem find(int userId, String restaurantId, String foodName) throws SQLException;
    ArrayList<CartItem> findAll(int userId) throws SQLException;
    void insert(CartItem cartItem) throws SQLException;
    public void delete(int userId, String restaurantId, String foodName) throws SQLException;
    void updateCount(int userId, String restaurantId, String foodName, int count) throws SQLException;
}
