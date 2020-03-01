package com.loghme.models.CartItem;

import com.loghme.models.Food.Food;
import com.loghme.models.Restaurant.Restaurant;

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

    public String getRestaurantId() {
        return restaurant.getId();
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