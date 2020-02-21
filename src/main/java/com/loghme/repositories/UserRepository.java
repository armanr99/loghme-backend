package com.loghme.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.CartItem.CartItem;
import com.loghme.configs.*;
import com.loghme.models.Food.Food;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.models.User.User;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.models.Wallet.Exceptions.WrongAmount;
import com.loghme.models.Wallet.Wallet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Gson gson;
    private User user;
    private static UserRepository instance = null;

    public static UserRepository getInstance() {
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private UserRepository() {
        gson = new Gson();
        user = getSampleUser();
    }

    private User getSampleUser() {
        int id = 0;
        String firstName = "Ehsan";
        String lastName = "KhamesPanah";
        String phoneNumber = "+989123456789";
        String email = "ekhamespanah@yahoo.com";
        Location location = new Location(0, 0);
        Wallet wallet = new Wallet(100000);
        return new User(id, firstName, lastName, phoneNumber, email, location, wallet);
    }

    public User getUser() {
        return user;
    }

    public void addToCart(String foodInfo) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange {
        JsonObject foodInfoObject = gson.fromJson(foodInfo, JsonObject.class);
        JsonElement foodNameElement = foodInfoObject.get(Fields.FOOD_NAME);
        JsonElement restaurantIdElement = foodInfoObject.get(Fields.RESTAURANT_Id);
        String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        addToCart(foodName, restaurantId);
    }

    public String getCart() {
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

    public String finalizeOrder() throws EmptyCartFinalize, NotEnoughBalance {
        String jsonCart = getCart();

        user.finalizeOrder();

        return jsonCart;
    }

    void addToCart(String foodName, String restaurantId) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange {
        Restaurant restaurant = RestaurantRepository.getInstance().getRestaurantInstanceIfInRange(restaurantId, user.getLocation(), Configs.VISIBLE_RESTAURANTS_DISTANCE);
        Food food = restaurant.getFood(foodName);

        if(food == null)
            throw new FoodDoesntExist(foodName, restaurantId);
        else
            user.addToCart(food, restaurant);
    }

    public void chargeUser(double amount) throws WrongAmount {
        user.charge(amount);
    }
}
