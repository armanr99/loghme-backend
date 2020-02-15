package com.loghme.Constants;

public class Path {
    public static class Web {
        public static final String USER = "/user";
        public static final String RESTAURANTS = "/restaurants";
        public static final String ONE_RESTAURANT = "/restaurants/:id";
        public static final String FOOD = "/food";
        public static final String CART = "/cart";
    }

    public static class Template {
        public static final String USER = "/velocity/user/one.vm";
        public static final String CART = "/velocity/cart/cart.vm";
        public static final String RESTAURANTS_ALL = "/velocity/restaurant/all.vm";
        public static final String RESTAURANTS_ONE = "/velocity/restaurant/one.vm";
        public static final String RESTAURANTS_NOT_FOUND = "/velocity/restaurant/notfound.vm";
    }
}