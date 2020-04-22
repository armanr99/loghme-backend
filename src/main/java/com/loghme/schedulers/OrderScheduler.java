package com.loghme.schedulers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loghme.configs.DeliveryConfigs;
import com.loghme.configs.ServerConfigs;
import com.loghme.models.domain.Delivery.Delivery;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.repositories.UserRepository;
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
        final Runnable deliverRequester = () -> {
            String deliveriesJson = HTTPRequester.get(ServerConfigs.DELIVERIES_URL);
            ArrayList<Delivery> deliveries = gson.fromJson(deliveriesJson, new TypeToken<ArrayList<Delivery>>() {}.getType());

            if(deliveries != null && deliveries.size() != 0) {
                scheduler.shutdown();
                assignDelivery(deliveries);
            }
        };

        scheduler.scheduleAtFixedRate(deliverRequester, 0, DeliveryConfigs.CHECK_TIME_SEC, TimeUnit.SECONDS);
    }

    private void assignDelivery(ArrayList<Delivery> deliveries) {
        Delivery bestDelivery = getBestDelivery(deliveries);
        order.setDelivery(bestDelivery);
    }

    private Delivery getBestDelivery(ArrayList<Delivery> deliveries) {
        Location restaurantLocation = order.getRestaurantLocation();
        Location userLocation = UserRepository.getInstance().getUser().getLocation();

        return deliveries.stream().min(Comparator.comparing(delivery -> delivery.getTotalTime(restaurantLocation, userLocation))).get();
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
