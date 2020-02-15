package com.loghme.CartItem;

import com.loghme.Food.Food;
import com.loghme.Restaurant.Restaurant;

public class CartItem {
    private Food food;
    private Restaurant restaurant;
    private int count;

    public CartItem(Food food, Restaurant restaurant) {
        this.count = 1;
        this.food = food;
        this.restaurant = restaurant;
    }

    public void increaseCount() {
        count++;
    }

    public String getFoodName() {
        return food.getName();
    }

    public String getRestaurantID() {
        return restaurant.getID();
    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

    public int getCount() {
        return count;
    }

    public double getTotalPrice() {
        return food.getPrice() * count;
    }
}
