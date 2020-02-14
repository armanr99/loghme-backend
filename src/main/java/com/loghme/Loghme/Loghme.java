package com.loghme.Loghme;

import com.loghme.Cart.Exceptions.DifferentRestaurant;
import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.Restaurant.*;
import com.loghme.Restaurant.Exceptions.FoodAlreadyExistsInRestaurant;
import com.loghme.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.User.UserRepository;

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

    public void addToCart(String foodInfo) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant {
        UserRepository.getInstance().addToCart(foodInfo);
    }

    public String getCart() {
        return UserRepository.getInstance().getCart();
    }

    public String finalizeOrder() throws EmptyCartFinalize {
        return UserRepository.getInstance().finalizeOrder();
    }

    public List<String> getRecommendedRestaurants() {
        return RestaurantRepository.getInstance().getRecommendedRestaurants();
    }
}
