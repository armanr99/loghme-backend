package com.loghme.configs;

public class Path {
    public static class Web {
        public static final String RESTAURANTS = "/restaurants";
        public static final String RESTAURANT = "/restaurants/*";
        public static final String USER = "/user";
    }

    public static class jsp {
        public static final String RESTAURANTS = "/views/Restaurant/restaurants.jsp";
        public static final String RESTAURANT = "/views/Restaurant/restaurant.jsp";
        public static final String ERROR = "/views/Error/error.jsp";
        public static final String USER = "/views/User/user.jsp";
    }
}