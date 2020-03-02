package com.loghme.repositories;

import com.loghme.models.Order.Order;
import com.loghme.schedulers.OrderScheduler;

import java.util.ArrayList;

public class DeliveryRepository {
    private ArrayList<OrderScheduler> orderSchedulers;
    private static DeliveryRepository instance = null;

    public static DeliveryRepository getInstance() {
        if (instance == null) {
            instance = new DeliveryRepository();
        }
        return instance;
    }

    private DeliveryRepository() {
        orderSchedulers = new ArrayList<>();
    }

    public void addDelivery(Order order) {
        OrderScheduler newScheduler = new OrderScheduler(order);
        newScheduler.handleOrder();
        orderSchedulers.add(newScheduler);
    }

    public void shutdownDeliveries() {
        for(OrderScheduler orderScheduler : orderSchedulers)
            orderScheduler.shutdown();
    }
}
