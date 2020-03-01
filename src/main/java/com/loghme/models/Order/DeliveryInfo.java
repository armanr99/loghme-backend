package com.loghme.models.Order;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.models.Delivery.Delivery;
import com.loghme.models.Location.Location;
import com.loghme.repositories.UserRepository;

import java.util.Date;

class DeliveryInfo {
    private Delivery delivery;
    private long totalTime;
    private Date startDate;

    DeliveryInfo(Delivery delivery, Location restaurantLocation) {
        this.delivery = delivery;
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        this.totalTime = delivery.getTotalTime(restaurantLocation, userLocation);
        this.startDate = new Date();
    }

    String getState() {
        return (getRemainingSeconds() == 0 ? DeliveryConfigs.State.DELIVERED : DeliveryConfigs.State.DELIVERING);
    }

    long getRemainingSeconds() {
        long timeDiff = new Date().getTime() - startDate.getTime();
        long timeDiffSeconds = timeDiff / 1000;

        return (totalTime - timeDiffSeconds < 0 ? 0 : totalTime - timeDiffSeconds);
    }
}