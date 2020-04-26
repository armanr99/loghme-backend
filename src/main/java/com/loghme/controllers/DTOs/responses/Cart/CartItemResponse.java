package com.loghme.controllers.DTOs.responses.Cart;

import com.loghme.controllers.DTOs.responses.Food.FoodResponse;
import com.loghme.controllers.DTOs.responses.Food.PartyFoodResponse;
import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantSmallResponse;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Restaurant.Restaurant;

import java.sql.SQLException;

public class CartItemResponse {
    private FoodResponse food;
    private RestaurantSmallResponse restaurant;
    private int count;

    CartItemResponse(Restaurant restaurant, CartItem cartItem) throws FoodDoesntExist, SQLException {
        setFood(restaurant, cartItem);
        this.restaurant = new RestaurantSmallResponse(restaurant);
        this.count = cartItem.getCount();
    }

    private void setFood(Restaurant restaurant, CartItem cartItem) throws FoodDoesntExist, SQLException {
        Food food = cartItem.getFood();
        if (food instanceof PartyFood) {
            this.food = new PartyFoodResponse(restaurant, (PartyFood) food);
        } else {
            this.food = new FoodResponse(restaurant, food);
        }
    }
}
