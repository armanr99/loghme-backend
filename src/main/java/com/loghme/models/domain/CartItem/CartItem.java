package com.loghme.models.domain.CartItem;

import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Restaurant.Restaurant;

public class CartItem {
    int userId;
    private Food food;
    private Restaurant restaurant;
    private int count;

    public CartItem(int userId, Food food, Restaurant restaurant) {
        this.userId  = userId;
        this.count = 1;
        this.food = food;
        this.restaurant = restaurant;
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

    public void finalizeItem() throws InvalidCount {
        food.sell(count);
    }
}
