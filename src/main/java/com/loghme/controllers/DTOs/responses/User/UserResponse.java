package com.loghme.controllers.DTOs.responses.User;

import com.loghme.controllers.DTOs.responses.Cart.CartResponse;
import com.loghme.controllers.DTOs.responses.Order.OrderResponse;
import com.loghme.exceptions.EmptyCart;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.User.User;

import java.util.ArrayList;

public class UserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Location location;
    private double credit;
    private CartResponse cart;
    private ArrayList<OrderResponse> orders;

    public UserResponse(User user) throws FoodDoesntExist, RestaurantDoesntExist, OrderItemDoesntExist {
        setInfo(user);
        setCart(user.getCart());
        setOrders(user);
    }

    private void setInfo(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.location = user.getLocation();
        this.credit = user.getCredit();
    }

    private void setCart(Cart cart) throws FoodDoesntExist, RestaurantDoesntExist {
        this.cart = new CartResponse(cart);
    }

    private void setOrders(User user) throws RestaurantDoesntExist, FoodDoesntExist, OrderItemDoesntExist {
        orders = new ArrayList<>();

        for(Order order : user.getOrders()) {
            orders.add(new OrderResponse(order));
        }
    }
}
