package com.loghme.Restaurant;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.loghme.Constants.Configs;
import com.loghme.Constants.Fields;
import com.loghme.Constants.GeneralConstants;
import com.loghme.Food.Food;
import com.loghme.Location.Location;
import com.loghme.Restaurant.Exceptions.*;
import com.loghme.User.User;
import com.loghme.User.UserRepository;
import com.loghme.Util.RestaurantUpdateManager;

import java.lang.reflect.Type;
import java.util.*;

public class RestaurantRepository {
    private Gson gson;
    private HashMap<String, Restaurant> restaurants;
    private static RestaurantRepository instance = null;

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    private RestaurantRepository() {
        gson = new Gson();
        restaurants = new HashMap<>();
    }

    public void fetchData(String sourceURL) throws JsonSyntaxException, RestaurantAlreadyExists {
        String restaurantsData = RestaurantUpdateManager.fetch(sourceURL);
        JsonArray restaurantsDataArray = gson.fromJson(restaurantsData, JsonArray.class);

        for (JsonElement restaurantDataElement : restaurantsDataArray) {
            this.addRestaurant(restaurantDataElement.toString());
        }
    }

    public static void clearInstance() {
        instance = null;
    }

    public void addRestaurant(String serializedRestaurant) throws JsonSyntaxException, RestaurantAlreadyExists {
        JsonObject restaurantObject = gson.fromJson(serializedRestaurant, JsonObject.class);
        JsonElement menuElement = restaurantObject.remove(Fields.MENU);
        Restaurant newRestaurant = gson.fromJson(restaurantObject.toString(), Restaurant.class);
        String newRestaurantID = newRestaurant.getID();

        if (restaurants.containsKey(newRestaurantID))
            throw new RestaurantAlreadyExists(newRestaurantID);

        Type listType = new TypeToken<List<Food>>() {}.getType();
        ArrayList<Food> menu = gson.fromJson(menuElement, listType);
        newRestaurant.addFoods(menu);

        restaurants.put(newRestaurantID, newRestaurant);
    }

    public void addFood(String serializedFood)
            throws JsonParseException, FoodAlreadyExistsInRestaurant, RestaurantDoesntExist {
        JsonObject foodWithRestaurantID = gson.fromJson(serializedFood, JsonObject.class);
        JsonElement restaurantIDElement = foodWithRestaurantID.remove(Fields.RESTAURANT_ID);
        String restaurantID = restaurantIDElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIDElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantID);
        restaurant.addFood(foodWithRestaurantID);
    }

    public List<String> getRestaurants() {
        return new ArrayList<>(restaurants.keySet());
    }

    public String getRestaurant(String restaurantInfo) throws JsonParseException, RestaurantDoesntExist {
        JsonElement restaurantIDElement = gson.fromJson(restaurantInfo, JsonObject.class).get(Fields.ID);
        String restaurantID = restaurantIDElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIDElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantID);
        JsonObject restaurantObject = gson.toJsonTree(restaurant, Restaurant.class).getAsJsonObject();
        restaurantObject.remove(Fields.MENU);
        JsonElement menuElement = gson.toJsonTree(restaurant.getListMenu());
        restaurantObject.add(Fields.MENU, menuElement);

        return gson.toJson(restaurantObject, JsonObject.class);
    }

    public String getFood(String foodInfo) throws JsonParseException, RestaurantDoesntExist, FoodDoesntExist {
        JsonElement restaurantIDElement = gson.fromJson(foodInfo, JsonObject.class).get(Fields.RESTAURANT_ID);
        JsonElement foodNameElement = gson.fromJson(foodInfo, JsonObject.class).get(Fields.FOOD_NAME);
        String restaurantID = restaurantIDElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIDElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantID);
        Food food = restaurant.getFood(foodName);

        if (food == null)
            throw new FoodDoesntExist(foodName, restaurantID);
        else
            return gson.toJson(restaurants.get(restaurantID).getFood(foodName));
    }

    public ArrayList<String> getRecommendedRestaurants() {
        User user = UserRepository.getInstance().getUser();
        ArrayList<String> recommendedRestaurants = new ArrayList<>();
        ArrayList<Double> popularities = new ArrayList<>();

        for (Restaurant restaurant : restaurants.values()) {
            double popularity = getPopularity(restaurant, user);
            if (recommendedRestaurants.size() < Configs.MAX_RECOMMENDED_SIZE) {
                recommendedRestaurants.add(restaurant.getName());
                popularities.add(popularity);
            } else {
                int minIndex = popularities.indexOf(Collections.min(popularities));
                if (popularity > popularities.get(minIndex)) {
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

    public Restaurant getRestaurantInstance(String restaurantID) throws RestaurantDoesntExist {
        if (!restaurants.containsKey(restaurantID))
            throw new RestaurantDoesntExist(restaurantID);
        else
            return restaurants.get(restaurantID);
    }

    public Restaurant getRestaurantInstanceIfInRange(String restaurantID, Location source, double distance) throws RestaurantDoesntExist, RestaurantOutOfRange {
        Restaurant restaurant = this.getRestaurantInstance(restaurantID);
        if (Double.compare(restaurant.getLocation().getEuclideanDistanceFrom(source), distance) <= 0) {
            System.out.println(restaurant.getLocation().getEuclideanDistanceFrom(source));
            return restaurant;
        }
        else
            throw new RestaurantOutOfRange();
    }

    ArrayList<Restaurant> getRestaurantsWithinDistance(Location source, double distance) {
        ArrayList<Restaurant> restaurantsWithinDistance = new ArrayList<>();

        for (Restaurant restaurant : restaurants.values())
            if (Double.compare(restaurant.getLocation().getEuclideanDistanceFrom(source), distance) <= 0)
                restaurantsWithinDistance.add(restaurant);

        return restaurantsWithinDistance;
    }
}
