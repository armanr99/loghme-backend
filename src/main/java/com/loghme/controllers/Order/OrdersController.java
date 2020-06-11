package com.loghme.controllers.Order;

import com.loghme.configs.PathConfigs;
import com.loghme.controllers.DTOs.responses.Order.OrderResponse;
import com.loghme.controllers.DTOs.responses.Order.OrdersResponse;
import com.loghme.exceptions.*;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping(PathConfigs.Web.ORDERS)
public class OrdersController {
    @GetMapping("")
    public OrdersResponse getOrders(@RequestAttribute int userId)
            throws UserDoesntExist, RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist,
                    SQLException, OrderDoesntExist {
        ArrayList<Order> orders = UserService.getInstance().getOrders(userId);
        return new OrdersResponse(orders);
    }

    @GetMapping("{id}")
    public OrderResponse getOrder(@PathVariable int id, @RequestAttribute int userId)
            throws OrderDoesntExist, RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist,
                    SQLException, UserDoesntExist, ForbiddenAccess {
        Order order = UserService.getInstance().getOrder(userId, id);
        return new OrderResponse(order);
    }
}
