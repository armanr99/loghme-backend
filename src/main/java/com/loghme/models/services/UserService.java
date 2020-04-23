package com.loghme.models.services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loghme.models.domain.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.configs.*;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.domain.User.exceptions.OrderDoesntExist;
import com.loghme.models.domain.User.User;
import com.loghme.models.domain.Wallet.exceptions.NotEnoughBalance;
import com.loghme.models.domain.Wallet.exceptions.WrongAmount;
import com.loghme.models.repositories.UserRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Gson gson;
    private static UserService instance = null;

    public static UserService getInstance() {
        if(instance == null)
            instance = new UserService();
        return instance;
    }

    public static void clearInstance() {
        instance = null;
        UserRepository.clearInstance();
    }

    private UserService() {
        gson = new Gson();
    }

    public User getUser() {
        return UserRepository.getInstance().getUser();
    }

    public void addToCart(String foodName, String restaurantId) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange, InvalidCount {
        User user = UserRepository.getInstance().getUser();
        Restaurant restaurant = RestaurantService.getInstance().getRestaurantInstanceIfInRange(restaurantId, user.getLocation(), Configs.VISIBLE_RESTAURANTS_DISTANCE);
        Food food = restaurant.getFood(foodName);

        if(food == null)
            throw new FoodDoesntExist(foodName, restaurantId);
        else
            user.addToCart(food, restaurant);
    }

    public void chargeUser(double amount) throws WrongAmount {
        User user = UserRepository.getInstance().getUser();
        user.charge(amount);
    }

    public Order getOrder(int orderId) throws OrderDoesntExist {
        User user = UserRepository.getInstance().getUser();
        return user.getOrder(orderId);
    }

    public void removeFromCart(String foodName, String restaurantId) throws CartItemDoesntExist {
        User user = UserRepository.getInstance().getUser();
        user.removeFromCart(foodName, restaurantId);
    }

    public void addToCartStr(String foodInfo) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange, InvalidCount {
        JsonObject foodInfoObject = gson.fromJson(foodInfo, JsonObject.class);
        JsonElement foodNameElement = foodInfoObject.get(Fields.FOOD_NAME);
        JsonElement restaurantIdElement = foodInfoObject.get(Fields.RESTAURANT_Id);
        String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        addToCart(foodName, restaurantId);
    }

    public String getCartStr() {
        User user = UserRepository.getInstance().getUser();
        ArrayList<CartItem> userCartItems = user.getCartItemsList();
        ArrayList<JsonObject> serializedUserCartItems = new ArrayList<>();

        for(CartItem cartItem : userCartItems) {
            JsonObject cartObject = new JsonObject();
            cartObject.addProperty(Fields.FOOD_NAME, cartItem.getFoodName());
            cartObject.addProperty(Fields.RESTAURANT_Id, cartItem.getRestaurantId());
            cartObject.addProperty(Fields.COUNT, cartItem.getCount());
            serializedUserCartItems.add(cartObject);
        }

        Type jsonObjectType = new TypeToken<List<JsonObject>>() {}.getType();
        JsonElement cartElement = gson.toJsonTree(serializedUserCartItems, jsonObjectType).getAsJsonArray();
        JsonObject cartJsonObject = new JsonObject();
        cartJsonObject.add(Fields.ITEMS, cartElement);

        return gson.toJson(cartJsonObject);
    }

    public String finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance, InvalidCount {
        String jsonCart = getCartStr();
        User user = UserRepository.getInstance().getUser();

        user.finalizeOrder();

        return jsonCart;
    }
}
