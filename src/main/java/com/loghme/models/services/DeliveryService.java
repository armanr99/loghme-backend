package com.loghme.models.services;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.Restaurant;
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
        for(OrderScheduler orderScheduler : orderSchedulers)
            orderScheduler.shutdown();
    }

    public long getExpectedDeliveryTime(Restaurant restaurant) {
        Location userLocation = UserService.getInstance().getUser().getLocation();
        Location restaurantLocation = restaurant.getLocation();
        double userRestaurantDistance = userLocation.getEuclideanDistanceFrom(restaurantLocation);

        return (long)((userRestaurantDistance * 1.5) / DeliveryConfigs.AVERAGE_SPEED) + DeliveryConfigs.AVERAGE_ASSIGN_TIME;
    }
}
