package com.loghme.models.domain.Order;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.models.domain.Delivery.Delivery;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.services.UserService;

import java.util.Date;

public class DeliveryInfo {
    private Delivery delivery;
    private long totalTime;
    private Date startDate;

    public DeliveryInfo(Delivery delivery, Location restaurantLocation) {
        this.delivery = delivery;
        Location userLocation = null;
        try {
            userLocation = UserService.getInstance().getUser(0).getLocation();
        } catch (UserDoesntExist userDoesntExist) {
            userDoesntExist.printStackTrace();
        }
        this.totalTime = delivery.getTotalTime(restaurantLocation, userLocation);
        this.startDate = new Date();
    }

    public long getTotalTime() {
        return totalTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    String getState() {
        return (getRemainingSeconds() == 0
                ? DeliveryConfigs.State.DELIVERED
                : DeliveryConfigs.State.DELIVERING);
    }

    private long getRemainingSeconds() {
        long timeDiff = new Date().getTime() - startDate.getTime();
        long timeDiffSeconds = timeDiff / 1000;

        return (totalTime - timeDiffSeconds < 0 ? 0 : totalTime - timeDiffSeconds);
    }
}
