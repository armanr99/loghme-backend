package com.loghme.models.domain.Delivery;

import com.loghme.models.domain.Location.Location;

public class Delivery {
    private String id;
    private double velocity;
    private Location location;

    public Delivery(String deliveryId, double velocity, Location location) {
        this.id = deliveryId;
        this.velocity = velocity;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public double getVelocity() {
        return velocity;
    }

    public Location getLocation() {
        return location;
    }

    public long getTotalTime(Location restaurantLocation, Location userLocation) {
        double totalDistance = getTotalDistance(restaurantLocation, userLocation);
        return (velocity == 0 ? 0 : (long) (totalDistance / velocity));
    }

    private double getTotalDistance(Location restaurantLocation, Location userLocation) {
        double toRestaurantDistance = location.getEuclideanDistanceFrom(restaurantLocation);
        double toUserDistance = location.getEuclideanDistanceFrom(userLocation);

        return (toRestaurantDistance + toUserDistance);
    }
}
