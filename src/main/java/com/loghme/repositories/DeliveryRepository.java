package com.loghme.repositories;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.Restaurant;
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

    public long getExpectedDeliveryTime(Restaurant restaurant) {
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        Location restaurantLocation = restaurant.getLocation();
        double userRestaurantDistance = userLocation.getEuclideanDistanceFrom(restaurantLocation);

        return (long)((userRestaurantDistance * 1.5) / DeliveryConfigs.AVERAGE_SPEED) + DeliveryConfigs.AVERAGE_ASSIGN_TIME;
    }
}
