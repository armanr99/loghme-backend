package com.loghme.controllers.wrappers.responses.Cart;

import com.loghme.controllers.wrappers.responses.Food.FoodResponse;
import com.loghme.controllers.wrappers.responses.Food.PartyFoodResponse;
import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantSmallResponse;
import com.loghme.models.CartItem.CartItem;
import com.loghme.models.Food.Food;
import com.loghme.models.Food.PartyFood;

public class CartItemResponse {
    private FoodResponse food;
    private RestaurantSmallResponse restaurant;
    private int count;

    public CartItemResponse(CartItem cartItem) {
        setFood(cartItem);
        this.restaurant = new RestaurantSmallResponse(cartItem.getRestaurant());
        this.count = cartItem.getCount();
    }

    private void setFood(CartItem cartItem) {
        Food food = cartItem.getFood();
        if(food instanceof PartyFood)
            this.food = new PartyFoodResponse(cartItem.getRestaurant(), (PartyFood)food);
        else
            this.food = new FoodResponse(cartItem.getRestaurant(), food);
    }
}
