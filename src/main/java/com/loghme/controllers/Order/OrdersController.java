package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.Order.OrderResponse;
import com.loghme.controllers.wrappers.responses.Order.OrdersResponse;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.User.exceptions.OrderDoesntExist;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.ORDERS)
public class OrdersController {
    @GetMapping("")
    public OrdersResponse getOrders() {
        ArrayList<Order> orders = UserService.getInstance().getUser().getOrdersList();
        return new OrdersResponse(orders);
    }

    @GetMapping("{id}")
    public OrderResponse getOrder(@PathVariable(value = "id") int id) throws OrderDoesntExist {
        Order order = UserService.getInstance().getOrder(id);
        return new OrderResponse(order);
    }
}