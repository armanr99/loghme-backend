package com.loghme.User;

import com.loghme.Cart.Cart;
import com.loghme.Cart.Exceptions.DifferentRestaurant;
import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.CartItem.CartItem;
import com.loghme.Food.Food;
import com.loghme.Location.Location;
import com.loghme.Restaurant.Restaurant;
import com.loghme.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.Wallet.Exceptions.WrongAmount;
import com.loghme.Wallet.Wallet;

import java.util.ArrayList;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Cart cart;
    private Location location;
    private Wallet wallet;

    public User(int id, String firstName, String lastName, String phoneNumber, String email, Location location, Wallet wallet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.wallet = wallet;
        this.cart = new Cart();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public double getCredit() {
        return wallet.getCredit();
    }

    public Location getLocation() {
        return location;
    }

    void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant {
        cart.addToCart(food, restaurant);
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cart.getCartItemsList();
    }

    void finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance {
        double totalPrice = cart.getTotalPrice();
        wallet.withdraw(totalPrice);
        cart.finalizeOrder();
    }

    void charge(double amount) throws WrongAmount {
        wallet.charge(amount);
    }
}
