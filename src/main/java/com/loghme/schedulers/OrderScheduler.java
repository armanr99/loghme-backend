package com.loghme.schedulers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loghme.configs.DeliveryConfigs;
import com.loghme.configs.ServerConfigs;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.models.domain.Delivery.Delivery;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Order.DeliveryInfo;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.services.UserService;
import com.loghme.utils.HTTPRequester;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderScheduler {
    private Gson gson;
    private Order order;
    private ScheduledExecutorService scheduler;

    public OrderScheduler(Order order) {
        this.order = order;
        this.gson = new Gson();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void handleOrder() {
        final Runnable deliverRequester =
                () -> {
                    String deliveriesJson = HTTPRequester.get(ServerConfigs.DELIVERIES_URL);
                    ArrayList<Delivery> deliveries =
                            gson.fromJson(
                                    deliveriesJson,
                                    new TypeToken<ArrayList<Delivery>>() {}.getType());

                    if (deliveries != null && deliveries.size() != 0) {
                        scheduler.shutdown();
                        try {
                            assignDelivery(deliveries);
                        } catch (UserDoesntExist
                                | RestaurantDoesntExist
                                | OrderItemDoesntExist ex) {
                            ex.printStackTrace();
                        }
                    }
                };

        scheduler.scheduleAtFixedRate(
                deliverRequester, 0, DeliveryConfigs.CHECK_TIME_SEC, TimeUnit.SECONDS);
    }

    private void assignDelivery(ArrayList<Delivery> deliveries)
            throws UserDoesntExist, OrderItemDoesntExist, RestaurantDoesntExist {
        Location restaurantLocation = order.getRestaurant().getLocation();
        Delivery bestDelivery = getBestDelivery(deliveries, restaurantLocation);
        DeliveryInfo deliveryInfo = new DeliveryInfo(bestDelivery, restaurantLocation);

        order.setDelivery(deliveryInfo);
    }

    private Delivery getBestDelivery(ArrayList<Delivery> deliveries, Location restaurantLocation)
            throws UserDoesntExist {
        assert (deliveries.size() > 0);
        Location userLocation = UserService.getInstance().getUser(0).getLocation();

        return deliveries.stream()
                .min(
                        Comparator.comparing(
                                delivery ->
                                        delivery.getTotalTime(restaurantLocation, userLocation)))
                .get();
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
