package com.loghme.models.User;

import com.loghme.models.Cart.Cart;
import com.loghme.models.Cart.Exceptions.CartItemDoesntExist;
import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Exceptions.InvalidCount;
import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Order.Order;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.models.User.Exceptions.OrderDoesntExist;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.models.Wallet.Exceptions.WrongAmount;
import com.loghme.models.Wallet.Wallet;
import com.loghme.repositories.DeliveryRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Cart cart;
    private Location location;
    private Wallet wallet;
    private HashMap<String, Order> orders;

    public User(int id, String firstName, String lastName, String phoneNumber, String email, Location location, Wallet wallet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.wallet = wallet;
        this.cart = new Cart();
        this.orders = new HashMap<>();
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

    public void addToCart(Food food, Restaurant restaurant) throws DifferentRestaurant, InvalidCount {
        cart.addToCart(food, restaurant);
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cart.getCartItemsList();
    }

    public void finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance, InvalidCount {
        try {
            double totalPrice = cart.getTotalPrice();
            wallet.withdraw(totalPrice);

            Order order = cart.finalizeOrder();
            orders.put(order.getId(), order);

            cart = new Cart();
            DeliveryRepository.getInstance().addDelivery(order);
        } catch(InvalidCount invalidCount) {
            cart = new Cart();
            throw invalidCount;
        }
    }

    public void charge(double amount) throws WrongAmount {
        wallet.charge(amount);
    }

    public Cart getCart() {
        return cart;
    }

    public ArrayList<Order> getOrdersList() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(String orderId) throws OrderDoesntExist {
        if (!orders.containsKey(orderId))
            throw new OrderDoesntExist(orderId);
        else
            return orders.get(orderId);
    }

    public void removeFromCart(String foodName, String restaurantId) throws CartItemDoesntExist {
        cart.removeItem(foodName, restaurantId);
    }
}
