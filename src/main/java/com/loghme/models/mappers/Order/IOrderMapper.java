package com.loghme.models.mappers.Order;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.Order.Order;

import java.sql.SQLException;

interface IOrderMapper extends IMapper<Order, Integer> {
    Order find(int orderId) throws SQLException;

    void insert(Order order) throws SQLException;
}
