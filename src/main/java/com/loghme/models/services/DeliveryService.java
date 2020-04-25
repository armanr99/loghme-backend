package com.loghme.models.services;

import com.loghme.models.domain.Order.Order;
import com.loghme.schedulers.OrderScheduler;

import java.util.ArrayList;

public class DeliveryService {
    private ArrayList<OrderScheduler> orderSchedulers;
    private static DeliveryService instance = null;

    public static DeliveryService getInstance() {
        if (instance == null) {
            instance = new DeliveryService();
        }
        return instance;
    }

    private DeliveryService() {
        orderSchedulers = new ArrayList<>();
    }

    public void addDelivery(Order order) {
        OrderScheduler newScheduler = new OrderScheduler(order);
        newScheduler.handleOrder();
        orderSchedulers.add(newScheduler);
    }

    public void shutdownDeliveries() {
        for (OrderScheduler orderScheduler : orderSchedulers) {
            orderScheduler.shutdown();
        }
    }
}
