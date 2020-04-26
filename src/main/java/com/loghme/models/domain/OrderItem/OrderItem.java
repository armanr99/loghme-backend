package com.loghme.models.domain.OrderItem;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.services.RestaurantService;

import java.sql.SQLException;

public class OrderItem {
    private String restaurantId;
    private String foodName;
    private int orderId;
    private int count;

    public OrderItem(int orderId, String restaurantId, String foodName, int count) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.foodName = foodName;
        this.count = count;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCount() {
        return count;
    }

    public Food getFood() throws FoodDoesntExist, SQLException {
        return RestaurantService.getInstance().getFood(restaurantId, foodName);
    }

    public double getTotalPrice() throws FoodDoesntExist, SQLException {
        return RestaurantService.getInstance().getFood(restaurantId, foodName).getPrice() * count;
    }
}
