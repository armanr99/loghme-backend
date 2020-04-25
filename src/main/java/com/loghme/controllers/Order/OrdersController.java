package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.configs.UserConfigs;
import com.loghme.controllers.DTOs.responses.Order.OrderResponse;
import com.loghme.controllers.DTOs.responses.Order.OrdersResponse;
import com.loghme.exceptions.*;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.ORDERS)
public class OrdersController {
    @GetMapping("")
    public OrdersResponse getOrders() throws UserDoesntExist, RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist {
        int userId = UserConfigs.DEFAULT_ID;
        ArrayList<Order> orders = UserService.getInstance().getOrders(userId);
        return new OrdersResponse(orders);
    }

    @GetMapping("{id}")
    public OrderResponse getOrder(@PathVariable(value = "id") int id) throws OrderDoesntExist, RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist {
        Order order = UserService.getInstance().getOrder(id);
        return new OrderResponse(order);
    }
}