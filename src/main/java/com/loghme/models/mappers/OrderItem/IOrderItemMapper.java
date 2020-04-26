package com.loghme.models.mappers.OrderItem;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.OrderItem.OrderItem;
import com.loghme.models.utils.TripleKey.TripleKey;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IOrderItemMapper extends IMapper<OrderItem, TripleKey> {
    OrderItem find(int orderId, String restaurantId, String foodName) throws SQLException;
    ArrayList<OrderItem> findAll(int orderId) throws SQLException;
    void insertBatch(ArrayList<OrderItem> orderItems) throws SQLException;
    OrderItem findFirst(int orderId) throws SQLException;
}