package com.loghme.User;

import com.loghme.Cart.Cart;
import com.loghme.Location.Location;

public class User {
    private Cart cart;
    private Location location;

    public User() {
        cart = new Cart();
        location = new Location(0, 0);
    }
}
