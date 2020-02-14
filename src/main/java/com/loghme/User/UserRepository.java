package com.loghme.User;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loghme.Cart.DifferentRestaurant;
import com.loghme.Cart.EmptyCartFinalize;
import com.loghme.CartItem.CartItem;
import com.loghme.Constants.Fields;
import com.loghme.Constants.GeneralConstants;
import com.loghme.Food.Food;
import com.loghme.Restaurant.FoodDoesntExist;
import com.loghme.Restaurant.RestaurantDoesntExist;
import com.loghme.Restaurant.Restaurant;
import com.loghme.Restaurant.RestaurantRepository;

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
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void addToCart(String foodInfo) throws RestaurantDoesntExist, FoodDoesntExist, DifferentRestaurant {
        JsonObject foodInfoObject = gson.fromJson(foodInfo, JsonObject.class);
        JsonElement foodNameElement = foodInfoObject.get(Fields.FOOD_NAME);
        JsonElement restaurantNameElement = foodInfoObject.get(Fields.RESTAURANT_NAME);
        String restaurantName = restaurantNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantNameElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        Restaurant restaurant = RestaurantRepository.getInstance().getRestaurantInstance(restaurantName);
        Food food = restaurant.getFood(foodName);

        if(food == null)
            throw new FoodDoesntExist(foodName, restaurantName);
        else
            user.addToCart(food, restaurant);
    }

    public String getCart() {
        ArrayList<CartItem> userCartItems = user.getCartItemsList();
        ArrayList<JsonObject> serializedUserCartItems = new ArrayList<>();

        for(CartItem cartItem : userCartItems) {
            JsonObject cartObject = new JsonObject();
            cartObject.addProperty(Fields.FOOD_NAME, cartItem.getFoodName());
            cartObject.addProperty(Fields.RESTAURANT_NAME, cartItem.getRestaurantName());
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
}
