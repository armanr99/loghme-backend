package com.loghme.User;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loghme.Cart.Exceptions.DifferentRestaurant;
import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.CartItem.CartItem;
import com.loghme.Constants.*;
import com.loghme.Food.Food;
import com.loghme.Location.Location;
import com.loghme.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.Restaurant.Restaurant;
import com.loghme.Restaurant.RestaurantRepository;
import com.loghme.Wallet.Wallet;

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

    public void addToCart(String foodInfo, RestaurantRepository restaurantRepository) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange {
        JsonObject foodInfoObject = gson.fromJson(foodInfo, JsonObject.class);
        JsonElement foodNameElement = foodInfoObject.get(Fields.FOOD_NAME);
        JsonElement restaurantIDElement = foodInfoObject.get(Fields.RESTAURANT_ID);
        String restaurantID = restaurantIDElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIDElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        addToCart(foodName, restaurantID, restaurantRepository);
    }

    public String getCart() {
        ArrayList<CartItem> userCartItems = user.getCartItemsList();
        ArrayList<JsonObject> serializedUserCartItems = new ArrayList<>();

        for(CartItem cartItem : userCartItems) {
            JsonObject cartObject = new JsonObject();
            cartObject.addProperty(Fields.FOOD_NAME, cartItem.getFoodName());
            cartObject.addProperty(Fields.RESTAURANT_ID, cartItem.getRestaurantID());
            cartObject.addProperty(Fields.COUNT, cartItem.getCount());
            serializedUserCartItems.add(cartObject);
        }

        Type jsonObjectType = new TypeToken<List<JsonObject>>() {}.getType();
        JsonElement cartElement = gson.toJsonTree(serializedUserCartItems, jsonObjectType).getAsJsonArray();
        JsonObject cartJsonObject = new JsonObject();
        cartJsonObject.add(Fields.ITEMS, cartElement);

        return gson.toJson(cartJsonObject);
    }

    public String finalizeOrder() throws EmptyCartFinalize {
        String jsonCart = getCart();

        user.finalizeOrder();

        return jsonCart;
    }

    void addToCart(String foodName, String restaurantID, RestaurantRepository restaurantRepository) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant, RestaurantOutOfRange {
        Restaurant restaurant = restaurantRepository.getRestaurantInstanceIfInRange(restaurantID, user.getLocation(), Configs.VISIBLE_RESTAURANTS_DISTANCE);
        Food food = restaurant.getFood(foodName);

        if(food == null)
            throw new FoodDoesntExist(foodName, restaurantID);
        else
            user.addToCart(food, restaurant);
    }


}
