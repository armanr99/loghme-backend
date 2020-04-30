package com.loghme.models.services;

import com.loghme.exceptions.*;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.configs.*;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.domain.User.User;
import com.loghme.models.mappers.User.UserMapper;
import com.loghme.models.repositories.OrderRepository;
import com.loghme.models.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private static UserService instance = null;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User getUser(int userId) throws UserDoesntExist, SQLException {
        return UserRepository.getInstance().getUser(userId);
    }

    public Cart getCart(int userId) throws UserDoesntExist, SQLException {
        User user = getUser(userId);
        return user.getCart();
    }

    public void addToCart(int userId, String restaurantId, String foodName)
            throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant,
            RestaurantOutOfRange, InvalidCount, UserDoesntExist, SQLException {
        User user = UserRepository.getInstance().getUser(userId);

        validateAddToCart(user, restaurantId, foodName);
        user.addToCart(restaurantId, foodName);
    }

    private void validateAddToCart(User user, String restaurantId, String foodName)
            throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist, InvalidCount, SQLException {
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
            throws CartItemDoesntExist, UserDoesntExist, SQLException {
        User user = UserRepository.getInstance().getUser(userId);
        user.removeFromCart(restaurantId, foodName);
    }

    public void chargeUser(int userId, double amount) throws WrongAmount, UserDoesntExist, SQLException {
        User user = UserRepository.getInstance().getUser(userId);
        user.chargeWallet(amount);
    }

    public ArrayList<Order> getOrders(int userId) throws UserDoesntExist, SQLException {
        User user = UserRepository.getInstance().getUser(userId);
        return user.getOrders();
    }

    public Order getOrder(int orderId) throws OrderDoesntExist, SQLException {
        return OrderRepository.getInstance().getOrder(orderId);
    }

    public void finalizeOrder(int userId)
            throws EmptyCart, NotEnoughBalance, InvalidCount, UserDoesntExist, WrongAmount,
            FoodDoesntExist, RestaurantDoesntExist, SQLException {
        User user = UserRepository.getInstance().getUser(userId);
        user.finalizeOrder();
    }

    public void signupUser(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException, EmailAlreadyExists {
        validateEmailDoesntExist(email);
        addUser(firstName, lastName, phoneNumber, email, password);
    }

    private void addUser(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(firstName, lastName, phoneNumber, email, hashedPassword);
        UserMapper.getInstance().insert(newUser);
    }

    private void validateEmailDoesntExist(String email) throws SQLException, EmailAlreadyExists {
        User emailUser = UserMapper.getInstance().findByEmail(email);
        if(emailUser != null) {
            throw new EmailAlreadyExists(email);
        }
    }
}
