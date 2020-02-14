package com.loghme.Restaurant;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.loghme.Constants.Configs;
import com.loghme.Constants.Fields;
import com.loghme.Constants.GeneralConstants;
import com.loghme.Food.Food;
import com.loghme.Location.Location;
import com.loghme.Restaurant.Exceptions.FoodAlreadyExistsInRestaurant;
import com.loghme.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.User.User;
import com.loghme.User.UserRepository;

import java.lang.reflect.Type;
import java.util.*;

public class RestaurantRepository {
    private Gson gson;
    private HashMap<String, Restaurant> restaurants;
    private static RestaurantRepository instance = null;

    public static RestaurantRepository getInstance() {
        if(instance == null)
            instance = new RestaurantRepository();
        return instance;
    }

    private RestaurantRepository() {
        gson = new Gson();
        restaurants = new HashMap<>();
    }

    public static void clearInstance() {
        instance = null;
    }

    public void addRestaurant(String serializedRestaurant) throws JsonSyntaxException, RestaurantAlreadyExists {
        JsonObject restaurantObject = gson.fromJson(serializedRestaurant, JsonObject.class);
        JsonElement menuElement = restaurantObject.remove(Fields.MENU);
        Restaurant newRestaurant = gson.fromJson(restaurantObject.toString(), Restaurant.class);
        String newRestaurantName = newRestaurant.getName();

        if (restaurants.containsKey(newRestaurantName))
            throw new RestaurantAlreadyExists(newRestaurantName);

        Type listType = new TypeToken<List<Food>>() {}.getType();
        ArrayList<Food> menu = gson.fromJson(menuElement, listType);
        newRestaurant.addFoods(menu);

        restaurants.put(newRestaurantName, newRestaurant);
    }

    public void addFood(String serializedFood)
            throws JsonParseException, FoodAlreadyExistsInRestaurant, RestaurantDoesntExist {
        JsonObject foodWithRestaurantName = gson.fromJson(serializedFood, JsonObject.class);
        JsonElement restaurantNameElement = foodWithRestaurantName.remove(Fields.RESTAURANT_NAME);
        String restaurantName = restaurantNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantNameElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantName);
        restaurant.addFood(foodWithRestaurantName);
    }

    public List<String> getRestaurants() {
        return new ArrayList<>(restaurants.keySet());
    }

    public String getRestaurant(String restaurantInfo) throws JsonParseException, RestaurantDoesntExist {
        JsonElement restaurantNameElement = gson.fromJson(restaurantInfo, JsonObject.class).get(Fields.NAME);
        String restaurantName = restaurantNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantNameElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantName);
        JsonObject restaurantObject = gson.toJsonTree(restaurant, Restaurant.class).getAsJsonObject();
        restaurantObject.remove(Fields.MENU);
        JsonElement menuElement = gson.toJsonTree(restaurant.getListMenu());
        restaurantObject.add(Fields.MENU, menuElement);

        return gson.toJson(restaurantObject, JsonObject.class);
    }

    public String getFood(String foodInfo) throws JsonParseException, RestaurantDoesntExist, FoodDoesntExist {
        JsonElement restaurantNameElement = gson.fromJson(foodInfo, JsonObject.class).get(Fields.RESTAURANT_NAME);
        JsonElement foodNameElement = gson.fromJson(foodInfo, JsonObject.class).get(Fields.FOOD_NAME);
        String restaurantName = restaurantNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantNameElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantName);
        Food food = restaurant.getFood(foodName);

        if(food == null)
            throw new FoodDoesntExist(foodName, restaurantName);
        else
            return gson.toJson(restaurants.get(restaurantName).getFood(foodName));
    }

    public ArrayList<String> getRecommendedRestaurants() {
        User user = UserRepository.getInstance().getUser();
        ArrayList<String> recommendedRestaurants = new ArrayList<>();
        ArrayList<Double> popularities = new ArrayList<>();

        for(Restaurant restaurant : restaurants.values()) {
            double popularity = getPopularity(restaurant, user);
            if(recommendedRestaurants.size() < Configs.MAX_RECOMMENDED_SIZE) {
                recommendedRestaurants.add(restaurant.getName());
                popularities.add(popularity);
            }
            else {
                int minIndex = popularities.indexOf(Collections.min(popularities));
                if(popularity > popularities.get(minIndex)) {
                    recommendedRestaurants.set(minIndex, restaurant.getName());
                    popularities.set(minIndex, popularity);
                }
            }
        }

        return recommendedRestaurants;
    }

    private double getPopularity(Restaurant restaurant, User user) {
        Location userLocation = user.getLocation();
        Location restaurantLocation = restaurant.getLocation();
        double distanceFromUser = userLocation.getEuclideanDistanceFrom(restaurantLocation);
        double averageFoodsPopularity = restaurant.getAverageFoodsPopulation();

        return (averageFoodsPopularity / distanceFromUser);
    }

    public Restaurant getRestaurantInstance(String restaurantName) throws RestaurantDoesntExist {
        if (!restaurants.containsKey(restaurantName))
            throw new RestaurantDoesntExist(restaurantName);
        else
            return restaurants.get(restaurantName);
    }
}
