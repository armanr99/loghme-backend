package com.loghme.configs;

public class Path {
    public static class Web {
        public static final String RESTAURANTS = "/restaurants";
        public static final String RESTAURANT = "/restaurants/*";
        public static final String USER = "/user";
        public static final String CART = "/cart";
        public static final String WALLET = "/wallet";
        public static final String ORDER = "/order";
        public static final String ORDER_SINGLE = "/order/*";
        public static final String FOOD_PARTY = "/foodparty";
    }

    public static class jsp {
        public static final String RESTAURANTS = "/views/Restaurant/restaurants.jsp";
        public static final String RESTAURANT = "/views/Restaurant/restaurant.jsp";
        public static final String ERROR = "/views/Error/error.jsp";
        public static final String USER = "/views/User/user.jsp";
        public static final String NON_EMPTY_CART = "/views/Cart/nonEmptyCart.jsp";
        public static final String EMPTY_CART = "/views/Cart/emptyCart.jsp";
        public static final String ORDER_SINGLE = "/views/Order/order.jsp";
        public static final String FOOD_PARTY = "/views/FoodParty/foodParty.jsp";
    }
}