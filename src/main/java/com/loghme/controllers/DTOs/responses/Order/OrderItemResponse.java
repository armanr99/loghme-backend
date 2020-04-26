package com.loghme.controllers.DTOs.responses.Order;

import com.loghme.controllers.DTOs.responses.Food.FoodResponse;
import com.loghme.controllers.DTOs.responses.Food.PartyFoodResponse;
import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantSmallResponse;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.OrderItem.OrderItem;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;

class OrderItemResponse {
    private FoodResponse food;
    private RestaurantSmallResponse restaurant;
    private int count;

    OrderItemResponse(Restaurant restaurant, OrderItem orderItem) throws FoodDoesntExist, SQLException {
        setFood(restaurant, orderItem);
        this.restaurant = new RestaurantSmallResponse(restaurant);
        this.count = orderItem.getCount();
    }

    private void setFood(Restaurant restaurant, OrderItem orderItem) throws FoodDoesntExist, SQLException {
        Food food = orderItem.getFood();
        if (food instanceof PartyFood) {
            this.food = new PartyFoodResponse(restaurant, (PartyFood) food);
        } else {
            this.food = new FoodResponse(restaurant, food);
        }
    }
}
