package com.loghme.controllers.wrappers.responses.User;

import com.loghme.controllers.wrappers.responses.Cart.CartWrapper;
import com.loghme.controllers.wrappers.responses.Order.OrderWrapper;
import com.loghme.models.Location.Location;
import com.loghme.models.Order.Order;
import com.loghme.models.User.User;

import java.util.ArrayList;

public class UserWrapper {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Location location;
    private double credit;
    private CartWrapper cart;
    private ArrayList<OrderWrapper> orders;

    public UserWrapper(User user) {
        setInfo(user);
        setCart(user);
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

    private void setCart(User user) {
        this.cart = new CartWrapper(user.getCart());
    }

    private void setOrders(User user) {
        orders = new ArrayList<>();

        for(Order order : user.getOrdersList()) {
            orders.add(new OrderWrapper(order));
        }
    }
}
