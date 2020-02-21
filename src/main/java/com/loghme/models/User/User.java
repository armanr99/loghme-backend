package com.loghme.models.User;

import com.loghme.models.Cart.Cart;
import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.models.Wallet.Exceptions.WrongAmount;
import com.loghme.models.Wallet.Wallet;

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

    public void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant {
        cart.addToCart(food, restaurant);
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cart.getCartItemsList();
    }

    public void finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance {
        double totalPrice = cart.getTotalPrice();
        wallet.withdraw(totalPrice);
        cart.finalizeOrder();
    }

    public void charge(double amount) throws WrongAmount {
        wallet.charge(amount);
    }
}
