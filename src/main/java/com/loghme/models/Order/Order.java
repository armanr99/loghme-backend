package com.loghme.models.Order;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.models.Cart.Cart;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Delivery.Delivery;
import com.loghme.models.Location.Location;

import java.util.ArrayList;

public class Order {
    private Cart cart;
    private String id;
    private DeliveryInfo deliveryInfo;

    public Order(Cart cart) {
        this.cart = cart;
        this.id = OrderIdHandler.getNextId();
        this.deliveryInfo = null;
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

    public String getId() {
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
}
