package com.loghme.models.Loghme;

import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.Food.Exceptions.InvalidCount;
import com.loghme.models.Restaurant.Exceptions.*;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.repositories.UserRepository;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;

import java.util.List;

public class Loghme {

    public void addRestaurant(String serializedRestaurant) throws RestaurantAlreadyExists {
        RestaurantRepository.getInstance().addRestaurant(serializedRestaurant);
    }

    public void addFood(String serializedFood) throws FoodAlreadyExistsInRestaurant, RestaurantDoesntExist {
        RestaurantRepository.getInstance().addFood(serializedFood);
    }

    public List<String> getRestaurants() {
        return RestaurantRepository.getInstance().getRestaurants();
    }

    public String getRestaurant(String restaurantInfo) throws RestaurantDoesntExist {
        return RestaurantRepository.getInstance().getRestaurant(restaurantInfo);
    }

    public String getFood(String foodInfo) throws FoodDoesntExist, RestaurantDoesntExist {
        return RestaurantRepository.getInstance().getFood(foodInfo);
    }

    public void addToCart(String foodInfo) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange, InvalidCount {
        UserRepository.getInstance().addToCart(foodInfo);
    }

    public String getCart() {
        return UserRepository.getInstance().getCart();
    }

    public String finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance, InvalidCount {
        return UserRepository.getInstance().finalizeOrder();
    }

    public List<String> getRecommendedRestaurants() {
        return RestaurantRepository.getInstance().getRecommendedRestaurants();
    }
}
