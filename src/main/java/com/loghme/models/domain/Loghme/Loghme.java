package com.loghme.models.domain.Loghme;

import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Restaurant.exceptions.*;
import com.loghme.models.services.RestaurantService;
import com.loghme.models.services.UserService;
import com.loghme.models.domain.Wallet.exceptions.NotEnoughBalance;

import java.util.List;

public class Loghme {

    public void addRestaurant(String serializedRestaurant) throws RestaurantAlreadyExists, FoodAlreadyExistsInRestaurant {
        RestaurantService.getInstance().addRestaurant(serializedRestaurant);
    }

    public void addFood(String serializedFood) throws FoodAlreadyExistsInRestaurant, RestaurantDoesntExist {
        RestaurantService.getInstance().addFoodStr(serializedFood);
    }

    public List<String> getRestaurants() {
        return RestaurantService.getInstance().getRestaurantsStr();
    }

    public String getRestaurant(String restaurantInfo) throws RestaurantDoesntExist {
        return RestaurantService.getInstance().getRestaurantStr(restaurantInfo);
    }

    public String getFood(String foodInfo) throws FoodDoesntExist, RestaurantDoesntExist {
        return RestaurantService.getInstance().getFoodStr(foodInfo);
    }

    public void addToCart(String foodInfo) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange, InvalidCount {
        UserService.getInstance().addToCartStr(foodInfo);
    }

    public String getCart() {
        return UserService.getInstance().getCartStr();
    }

    public String finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance, InvalidCount {
        return UserService.getInstance().finalizeOrder();
    }

    public List<String> getRecommendedRestaurants() {
        return RestaurantService.getInstance().getRecommendedRestaurants();
    }
}
