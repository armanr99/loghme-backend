package com.loghme.models.domain.CartItem;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.InvalidCount;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.CartRepository;
import com.loghme.models.repositories.RestaurantRepository;
import com.loghme.models.services.RestaurantService;

public class CartItem {
    private int count;
    private int userId;
    private String foodName;
    private String restaurantId;

    public CartItem(int userId, String restaurantId, String foodName) {
        this.userId = userId;
        this.foodName = foodName;
        this.restaurantId = restaurantId;
        this.count = 1;
    }

    public int getCount() {
        return count;
    }

    public int getUserId() {
        return userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void increaseCount() {
        count++;
        CartRepository.getInstance().updateCartItem(this);
    }

    public void decreaseCount() {
        count--;
        if (count > 0) {
            CartRepository.getInstance().updateCartItem(this);
        } else {
            CartRepository.getInstance().deleteCartItem(userId, restaurantId, foodName);
        }
    }

    public double getTotalPrice() throws FoodDoesntExist {
        return RestaurantService.getInstance().getFood(restaurantId, foodName).getPrice() * count;
    }

    public void finalizeItem() throws RestaurantDoesntExist, InvalidCount, FoodDoesntExist {
        Restaurant restaurant = RestaurantRepository.getInstance().getRestaurant(restaurantId);
        Food food = restaurant.getFood(foodName);

        food.sell(count);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Food getFood() throws FoodDoesntExist {
        return RestaurantService.getInstance().getFood(restaurantId, foodName);
    }
}
