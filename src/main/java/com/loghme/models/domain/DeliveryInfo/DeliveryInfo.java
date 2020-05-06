package com.loghme.models.domain.DeliveryInfo;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.exceptions.OrderDoesntExist;
import com.loghme.exceptions.OrderItemDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.models.domain.Delivery.Delivery;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.domain.User.User;
import com.loghme.models.repositories.OrderRepository;

import java.sql.SQLException;
import java.util.Date;

public class DeliveryInfo {
    private int orderId;
    private Date startDate;
    private Delivery delivery;

    public DeliveryInfo(
            int orderId, String deliveryId, double velocity, Location location, Date startDate) {
        this.orderId = orderId;
        this.startDate = startDate;
        this.delivery = new Delivery(deliveryId, velocity, location);
    }

    public DeliveryInfo(int orderId, Delivery delivery) {
        this.orderId = orderId;
        this.delivery = delivery;
        this.startDate = new Date();
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getDeliveryId() {
        return delivery.getId();
    }

    public double getVelocity() {
        return delivery.getVelocity();
    }

    public Location getLocation() {
        return delivery.getLocation();
    }

    public String getState()
            throws RestaurantDoesntExist, SQLException, UserDoesntExist, OrderItemDoesntExist,
                    OrderDoesntExist {
        return (getRemainingSeconds() == 0
                ? DeliveryConfigs.State.DELIVERED
                : DeliveryConfigs.State.DELIVERING);
    }

    private long getRemainingSeconds()
            throws SQLException, OrderItemDoesntExist, UserDoesntExist, RestaurantDoesntExist,
                    OrderDoesntExist {
        long timeDiff = new Date().getTime() - startDate.getTime();
        long timeDiffSeconds = timeDiff / 1000;
        long totalTime = getTotalTime();

        return (totalTime - timeDiffSeconds < 0 ? 0 : totalTime - timeDiffSeconds);
    }

    private long getTotalTime()
            throws RestaurantDoesntExist, OrderItemDoesntExist, SQLException, UserDoesntExist,
                    OrderDoesntExist {
        Restaurant restaurant = OrderRepository.getInstance().getOrderRestaurant(orderId);
        User user = OrderRepository.getInstance().getOrderUser(orderId);

        return delivery.getTotalTime(restaurant.getLocation(), user.getLocation());
    }
}
