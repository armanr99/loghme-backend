package com.loghme.models.services;

import com.loghme.exceptions.*;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.configs.*;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.domain.User.User;
import com.loghme.models.repositories.OrderRepository;
import com.loghme.models.repositories.UserRepository;

import java.util.ArrayList;

public class UserService {
    private static UserService instance = null;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User getUser(int userId) throws UserDoesntExist {
        return UserRepository.getInstance().getUser(userId);
    }

    public Cart getCart(int userId) throws UserDoesntExist {
        User user = getUser(userId);
        return user.getCart();
    }

    public void addToCart(int userId, String restaurantId, String foodName)
            throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant,
                    RestaurantOutOfRange, InvalidCount, UserDoesntExist {
        User user = UserRepository.getInstance().getUser(userId);

        validateAddToCart(user, restaurantId, foodName);
        user.addToCart(restaurantId, foodName);
    }

    private void validateAddToCart(User user, String restaurantId, String foodName)
            throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist, InvalidCount {
        Restaurant restaurant =
                RestaurantService.getInstance()
                        .getRestaurantInstanceIfInRange(
                                restaurantId,
                                user.getLocation(),
                                Configs.VISIBLE_RESTAURANTS_DISTANCE);
        Food food = restaurant.getFood(foodName);

        int cartItemCount = user.getCartItemCount(restaurantId, foodName);
        food.validateCount(cartItemCount + 1);
    }

    public void removeFromCart(int userId, String restaurantId, String foodName)
            throws CartItemDoesntExist, UserDoesntExist {
        User user = UserRepository.getInstance().getUser(userId);
        user.removeFromCart(restaurantId, foodName);
    }

    public void chargeUser(int userId, double amount) throws WrongAmount, UserDoesntExist {
        User user = UserRepository.getInstance().getUser(userId);
        user.chargeWallet(amount);
    }

    public ArrayList<Order> getOrders(int userId) throws UserDoesntExist {
        User user = UserRepository.getInstance().getUser(userId);
        return user.getOrders();
    }

    public Order getOrder(int orderId) throws OrderDoesntExist {
        return OrderRepository.getInstance().getOrder(orderId);
    }

    public void finalizeOrder(int userId)
            throws EmptyCart, NotEnoughBalance, InvalidCount, UserDoesntExist, WrongAmount,
                    FoodDoesntExist, RestaurantDoesntExist {
        User user = UserRepository.getInstance().getUser(userId);
        user.finalizeOrder();
    }
}
