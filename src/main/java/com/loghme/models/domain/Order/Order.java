package com.loghme.models.domain.Order;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.Delivery.Delivery;
import com.loghme.models.domain.Location.Location;

import java.util.ArrayList;

public class Order {
    private Cart cart;
    private int id;
    private DeliveryInfo deliveryInfo;

    public Order(Cart cart) {
        this.cart = cart;
        this.id = OrderIdHandler.getNextId();
        this.deliveryInfo = null;
    }

    public Order(int id, int userId) {
        //TODO
    }

    public void setDelivery(Delivery delivery) {
        deliveryInfo = new DeliveryInfo(delivery, cart.getRestaurantLocation());
    }

    public Location getRestaurantLocation() {
        return cart.getRestaurantLocation();
    }

    public String getState() {
        return (deliveryInfo == null ? DeliveryConfigs.State.SEARCHING : deliveryInfo.getState());
    }

    public int getId() {
        return id;
    }

    public String getRestaurantName() {
        return cart.getRestaurantName();
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cart.getCartItemsList();
    }

    public Cart getCart() {
        return cart;
    }

    public double getRemainingSeconds() {
        return (deliveryInfo == null ? -1 : deliveryInfo.getRemainingSeconds());
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public int getUserId() {
        return 0; //TODO
    }
}
