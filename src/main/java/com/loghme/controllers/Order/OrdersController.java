package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.Order.OrderWrapper;
import com.loghme.controllers.wrappers.Order.OrdersWrapper;
import com.loghme.controllers.wrappers.User.UserWrapper;
import com.loghme.models.Order.Order;
import com.loghme.models.User.Exceptions.OrderDoesntExist;
import com.loghme.models.User.User;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.ORDERS)
public class OrdersController {
    @GetMapping("")
    public OrdersWrapper getOrders() {
        ArrayList<Order> orders = UserRepository.getInstance().getUser().getOrdersList();
        return new OrdersWrapper(orders);
    }

    @GetMapping("{id}")
    public OrderWrapper getOrder(@PathVariable(value = "id") String id) throws OrderDoesntExist {
        Order order = UserRepository.getInstance().getOrder(id);
        return new OrderWrapper(order);
    }
}