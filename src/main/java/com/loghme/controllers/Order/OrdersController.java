package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.Order.OrderResponse;
import com.loghme.controllers.wrappers.responses.Order.OrdersResponse;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.User.exceptions.OrderDoesntExist;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.ORDERS)
public class OrdersController {
    @GetMapping("")
    public OrdersResponse getOrders() {
        ArrayList<Order> orders = UserRepository.getInstance().getUser().getOrdersList();
        return new OrdersResponse(orders);
    }

    @GetMapping("{id}")
    public OrderResponse getOrder(@PathVariable(value = "id") String id) throws OrderDoesntExist {
        Order order = UserRepository.getInstance().getOrder(id);
        return new OrderResponse(order);
    }
}