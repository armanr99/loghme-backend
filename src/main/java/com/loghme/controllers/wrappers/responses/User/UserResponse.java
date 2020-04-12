package com.loghme.controllers.wrappers.responses.User;

import com.loghme.controllers.wrappers.responses.Cart.CartItemResponse;
import com.loghme.controllers.wrappers.responses.Cart.CartResponse;
import com.loghme.controllers.wrappers.responses.Order.OrderResponse;
import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Location.Location;
import com.loghme.models.Order.Order;
import com.loghme.models.User.User;

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

    public UserResponse(User user) {
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

    private void setCart(Cart cart) {
        this.cart = new CartResponse(cart);
    }

    private void setOrders(User user) {
        orders = new ArrayList<>();

        for(Order order : user.getOrdersList()) {
            orders.add(new OrderResponse(order));
        }
    }
}
