package com.loghme.models.domain.CartItem;

import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Restaurant.Restaurant;

public class CartItem {
    int userId;
    private Food food;
    private Restaurant restaurant;
    String restaurantId;
    String foodName;
    private int count;

    public CartItem(int userId, Food food, Restaurant restaurant) {
        this.userId  = userId;
        this.count = 1;
        this.food = food;
        this.restaurant = restaurant;
    }

    public CartItem(int userId, String restaurantId, String foodName, int count) {
        this.userId  = userId;
        this.restaurantId = restaurantId;
        this.foodName = foodName;
        this.count = count;
    }

    public void increaseCount() {
        count++;
    }

    public void decreaseCount() {
        count--;
    }

    public String getFoodName() {
        return food.getName();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getRestaurantId() {
        return restaurant.getId();
    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

    public Food getFood() {
        return food;
    }

    public int getCount() {
        return count;
    }

    public double getTotalPrice() {
        return food.getPrice() * count;
    }

    public int getUserId() {
        return userId;
    }

    public void finalizeItem() throws InvalidCount {
        food.sell(count);
    }

    public void setCount(int count) {
        this.count = count;
    }
}
