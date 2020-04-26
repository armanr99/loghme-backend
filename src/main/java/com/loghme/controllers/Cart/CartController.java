package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.configs.UserConfigs;
import com.loghme.controllers.DTOs.requests.Cart.CartRequest;
import com.loghme.controllers.DTOs.responses.Cart.CartResponse;
import com.loghme.controllers.DTOs.responses.Order.OrdersResponse;
import com.loghme.exceptions.*;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.CART)
public class CartController {
    @GetMapping("")
    public CartResponse getCart() throws UserDoesntExist, FoodDoesntExist, RestaurantDoesntExist, SQLException {
        int userId = UserConfigs.DEFAULT_ID;
        Cart cart = UserService.getInstance().getCart(userId);
        return new CartResponse(cart);
    }

    @PostMapping("")
    public CartResponse addToCart(@RequestBody CartRequest request)
            throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist,
            DifferentRestaurant, InvalidCount, UserDoesntExist, SQLException {
        int userId = UserConfigs.DEFAULT_ID;
        String restaurantId = request.getRestaurantId();
        String foodName = request.getFoodName();

        UserService.getInstance().addToCart(userId, restaurantId, foodName);

        Cart cart = UserService.getInstance().getCart(userId);
        return new CartResponse(cart);
    }

    @DeleteMapping("")
    public CartResponse removeFromCart(@RequestBody CartRequest request)
            throws CartItemDoesntExist, UserDoesntExist, FoodDoesntExist, RestaurantDoesntExist, SQLException {
        int userId = UserConfigs.DEFAULT_ID;
        String restaurantId = request.getRestaurantId();
        String foodName = request.getFoodName();

        UserService.getInstance().removeFromCart(userId, restaurantId, foodName);

        Cart cart = UserService.getInstance().getCart(userId);
        return new CartResponse(cart);
    }

    @PostMapping("/order")
    public OrdersResponse finalizeOrder()
            throws InvalidCount, EmptyCart, NotEnoughBalance, UserDoesntExist,
            RestaurantDoesntExist, FoodDoesntExist, WrongAmount, OrderItemDoesntExist, SQLException, OrderDoesntExist {
        int userId = UserConfigs.DEFAULT_ID;
        UserService.getInstance().finalizeOrder(userId);

        ArrayList<Order> orders = UserService.getInstance().getOrders(userId);
        return new OrdersResponse(orders);
    }
}
