package com.loghme.models.domain.Location;

public class Location {
    private double x;
    private double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getEuclideanDistanceFrom(Location otherLocation) {
        double xDiff = x - otherLocation.x;
        double yDiff = y - otherLocation.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
