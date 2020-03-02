package com.loghme.configs;

public class DeliveryConfigs {
    public static final int CHECK_TIME_SEC = 30;
    public static final double AVERAGE_SPEED = 5;
    public static final int AVERAGE_ASSIGN_TIME = 60;

    public static class State {
        public static final String SEARCHING = "Searching";
        public static final String DELIVERING = "Delivering";
        public static final String DELIVERED = "Delivered";
    }
}