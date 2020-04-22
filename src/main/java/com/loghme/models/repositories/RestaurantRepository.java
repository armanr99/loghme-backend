package com.loghme.models.repositories;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.loghme.configs.Configs;
import com.loghme.configs.Fields;
import com.loghme.configs.GeneralConstants;
import com.loghme.configs.ServerConfigs;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.exceptions.*;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.domain.User.User;
import com.loghme.utils.HTTPRequester;

import java.lang.reflect.Type;
import java.util.*;

public class RestaurantRepository {
    private Gson gson;
    private HashMap<String, Restaurant> restaurants;
    private ArrayList<Restaurant> foodPartyRestaurants;
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
        foodPartyRestaurants = new ArrayList<>();
    }

    public void fetchData(String sourceURL) throws JsonSyntaxException, RestaurantAlreadyExists {
        String restaurantsData = HTTPRequester.get(sourceURL);
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
        String newRestaurantId = newRestaurant.getId();

        if (restaurants.containsKey(newRestaurantId))
            throw new RestaurantAlreadyExists(newRestaurantId);

        Type listType = new TypeToken<List<Food>>() {}.getType();
        ArrayList<Food> menu = gson.fromJson(menuElement, listType);
        newRestaurant.addFoods(menu);

        restaurants.put(newRestaurantId, newRestaurant);
    }

    public void addFood(String serializedFood)
            throws JsonParseException, FoodAlreadyExistsInRestaurant, RestaurantDoesntExist {
        JsonObject foodWithRestaurantId = gson.fromJson(serializedFood, JsonObject.class);
        JsonElement restaurantIdElement = foodWithRestaurantId.remove(Fields.RESTAURANT_Id);
        String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantId);
        restaurant.addFood(foodWithRestaurantId);
    }

    public List<String> getRestaurants() {
        return new ArrayList<>(restaurants.keySet());
    }

    public String getRestaurant(String restaurantInfo) throws JsonParseException, RestaurantDoesntExist {
        JsonElement restaurantIdElement = gson.fromJson(restaurantInfo, JsonObject.class).get(Fields.Id);
        String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantId);
        JsonObject restaurantObject = gson.toJsonTree(restaurant, Restaurant.class).getAsJsonObject();
        restaurantObject.remove(Fields.MENU);
        JsonElement menuElement = gson.toJsonTree(restaurant.getListMenu());
        restaurantObject.add(Fields.MENU, menuElement);

        return gson.toJson(restaurantObject, JsonObject.class);
    }

    public String getFood(String foodInfo) throws JsonParseException, RestaurantDoesntExist, FoodDoesntExist {
        JsonElement restaurantIdElement = gson.fromJson(foodInfo, JsonObject.class).get(Fields.RESTAURANT_Id);
        JsonElement foodNameElement = gson.fromJson(foodInfo, JsonObject.class).get(Fields.FOOD_NAME);
        String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();
        String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();

        Restaurant restaurant = getRestaurantInstance(restaurantId);
        Food food = restaurant.getFood(foodName);

        if (food == null)
            throw new FoodDoesntExist(foodName, restaurantId);
        else
            return gson.toJson(restaurants.get(restaurantId).getFood(foodName));
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

    public Restaurant getRestaurantInstance(String restaurantId) throws RestaurantDoesntExist {
        if (!restaurants.containsKey(restaurantId))
            throw new RestaurantDoesntExist(restaurantId);
        else
            return restaurants.get(restaurantId);
    }

    public Restaurant getRestaurantInstanceIfInRange(String restaurantId, Location source, double distance) throws RestaurantDoesntExist, RestaurantOutOfRange {
        Restaurant restaurant = this.getRestaurantInstance(restaurantId);
        if (Double.compare(restaurant.getLocation().getEuclideanDistanceFrom(source), distance) <= 0)
            return restaurant;
        else
            throw new RestaurantOutOfRange();
    }

    public ArrayList<Restaurant> getRestaurantsWithinDistance(Location source, double distance) {
        ArrayList<Restaurant> restaurantsWithinDistance = new ArrayList<>();

        for (Restaurant restaurant : restaurants.values())
            if (Double.compare(restaurant.getLocation().getEuclideanDistanceFrom(source), distance) <= 0)
                restaurantsWithinDistance.add(restaurant);

        return restaurantsWithinDistance;
    }

    public void fetchFoodParties() throws JsonSyntaxException, RestaurantDoesntExist {
        String restaurantsData = HTTPRequester.get(ServerConfigs.FOOD_PARTY_URL);
        JsonArray restaurantsDataArray = gson.fromJson(restaurantsData, JsonArray.class);

        for (JsonElement restaurantDataElement : restaurantsDataArray) {
            this.handleFoodPartyRestaurant(restaurantDataElement.toString());
        }
    }

    private void handleFoodPartyRestaurant(String serializedRestaurant) throws JsonSyntaxException, RestaurantDoesntExist {
        JsonObject restaurantObject = gson.fromJson(serializedRestaurant, JsonObject.class);
        String restaurantMenuJson = restaurantObject.remove(Fields.MENU).toString();
        restaurantObject.add(Fields.MENU, new JsonArray());

        addRestaurantIfDoesntExist(restaurantObject.toString());

        String restaurantId = restaurantObject.get(Fields.Id).getAsString();
        addPartyFoods(restaurantId, restaurantMenuJson);
    }

    private void addRestaurantIfDoesntExist(String serializedRestaurant) {
        try {
            addRestaurant(serializedRestaurant);
        } catch (RestaurantAlreadyExists ignored) {}
    }

    private void addPartyFoods(String restaurantId, String serializedMenu) throws RestaurantDoesntExist {
        Restaurant restaurant = getRestaurantInstance(restaurantId);
        foodPartyRestaurants.add(restaurant);

        Type listType = new TypeToken<List<PartyFood>>() {}.getType();
        ArrayList<PartyFood> menu = gson.fromJson(serializedMenu, listType);
        restaurant.addPartyFoods(menu);
    }

    public void clearPartyFoods() {
        for(Restaurant restaurant : foodPartyRestaurants)
            restaurant.clearPartyFoods();

        foodPartyRestaurants.clear();
    }

    public ArrayList<Restaurant> getFoodPartyRestaurants() {
        return foodPartyRestaurants;
    }
}
