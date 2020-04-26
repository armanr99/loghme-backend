package com.loghme.models.mappers.Order;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.Order.Order;

import java.sql.SQLException;
import java.util.ArrayList;

interface IOrderMapper extends IMapper<Order> {
    Order find(int orderId) throws SQLException;

    void insert(Order order) throws SQLException;

    ArrayList<Order> findAll(int userId) throws SQLException;
}
