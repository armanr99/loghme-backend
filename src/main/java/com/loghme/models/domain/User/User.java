package com.loghme.models.domain.User;

import com.loghme.exceptions.*;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Wallet.Wallet;
import com.loghme.models.repositories.OrderRepository;
import com.loghme.models.repositories.UserRepository;

import java.util.ArrayList;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Location location;
    private Wallet wallet;
    private Cart cart;

    public User(
            int id,
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            double credit,
            Location location) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.wallet = new Wallet(credit);
        this.cart = new Cart(id);
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

    public Cart getCart() {
        return cart;
    }

    public void addToCart(String restaurantId, String foodName)
            throws DifferentRestaurant, InvalidCount {
        cart.addItem(restaurantId, foodName);
    }

    public int getCartItemCount(String restaurantId, String foodName) {
        return cart.getCartItemCount(restaurantId, foodName);
    }

    public void removeFromCart(String restaurantId, String foodName) throws CartItemDoesntExist {
        cart.removeItem(restaurantId, foodName);
    }

    public void chargeWallet(double amount) throws WrongAmount {
        wallet.charge(amount);
    }

    public ArrayList<Order> getOrders() {
        return OrderRepository.getInstance().getOrders(this.id);
    }

    public void finalizeOrder()
            throws EmptyCart, NotEnoughBalance, InvalidCount, FoodDoesntExist, WrongAmount,
                    RestaurantDoesntExist {
        double totalPrice = cart.getTotalPrice();
        wallet.withdraw(totalPrice);

        try {
            cart.finalizeOrder();
            UserRepository.getInstance().updateUser(this);
        } catch (InvalidCount | RestaurantDoesntExist | FoodDoesntExist ex) {
            chargeWallet(totalPrice);
            UserRepository.getInstance().updateUser(this);
            throw ex;
        }
    }
}
