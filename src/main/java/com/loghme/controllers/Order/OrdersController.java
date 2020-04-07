package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.Order.OrdersWrapper;
import com.loghme.controllers.wrappers.User.UserWrapper;
import com.loghme.models.Order.Order;
import com.loghme.models.User.User;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
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
}