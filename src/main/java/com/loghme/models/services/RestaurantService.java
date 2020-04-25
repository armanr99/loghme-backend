package com.loghme.models.services;

import com.google.gson.*;
import com.loghme.configs.ServerConfigs;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.exceptions.RestaurantOutOfRange;
import com.loghme.models.DTOs.Restaurant.FoodPartyRestaurantInput;
import com.loghme.models.DTOs.Restaurant.RestaurantInput;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.FoodRepository;
import com.loghme.models.repositories.PartyFoodRepository;
import com.loghme.models.repositories.RestaurantRepository;
import com.loghme.schedulers.FoodPartyScheduler;
import com.loghme.utils.HTTPRequester;

import java.util.*;

public class RestaurantService {
    private Gson gson;
    private static RestaurantService instance = null;

    public static RestaurantService getInstance() {
        if (instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }

    private RestaurantService() {
        gson = new Gson();
    }

    public void fetchRestaurants(String sourceURL) throws JsonSyntaxException {
        String restaurantsData = HTTPRequester.get(sourceURL);
        RestaurantInput[] restaurantInputs =
                gson.fromJson(restaurantsData, RestaurantInput[].class);

        for (RestaurantInput restaurantInput : restaurantInputs) {
            RestaurantRepository.getInstance().addRestaurant(restaurantInput);
        }
    }

    public Restaurant getRestaurantInstanceIfInRange(
            String restaurantId, Location source, double distance)
            throws RestaurantDoesntExist, RestaurantOutOfRange {
        Restaurant restaurant = RestaurantRepository.getInstance().getRestaurant(restaurantId);

        if (isRestaurantInRange(restaurant, source, distance)) {
            return restaurant;
        } else {
            throw new RestaurantOutOfRange();
        }
    }

    public ArrayList<Restaurant> getRestaurantsWithinDistance(Location source, double distance) {
        ArrayList<Restaurant> restaurantsWithinDistance = new ArrayList<>();

        for (Restaurant restaurant : RestaurantRepository.getInstance().getRestaurants()) {
            if (isRestaurantInRange(restaurant, source, distance)) {
                restaurantsWithinDistance.add(restaurant);
            }
        }

        return restaurantsWithinDistance;
    }

    private boolean isRestaurantInRange(Restaurant restaurant, Location source, double distance) {
        return (Double.compare(restaurant.getLocation().getEuclideanDistanceFrom(source), distance)
                <= 0);
    }

    public ArrayList<PartyFood> getPartyFoods() {
        return PartyFoodRepository.getInstance().getPartyFoods();
    }

    public long getRemainingFoodPartySeconds() {
        return FoodPartyScheduler.getInstance().getRemainingSeconds();
    }

    public void fetchPartyFoods() throws JsonSyntaxException {
        String restaurantsData = HTTPRequester.get(ServerConfigs.FOOD_PARTY_URL);
        FoodPartyRestaurantInput[] foodPartyRestaurantInputs =
                gson.fromJson(restaurantsData, FoodPartyRestaurantInput[].class);

        for (FoodPartyRestaurantInput foodPartyRestaurantInput : foodPartyRestaurantInputs) {
            RestaurantRepository.getInstance().addFoodPartyRestaurant(foodPartyRestaurantInput);
        }
    }

    public void deletePartyFoods() {
        PartyFoodRepository.getInstance().deletePartyFoods();
    }

    public Food getFood(String restaurantId, String foodName) throws FoodDoesntExist {
        try {
            return PartyFoodRepository.getInstance().getPartyFood(restaurantId, foodName);
        } catch (FoodDoesntExist foodDoesntExist) {
            return FoodRepository.getInstance().getFood(restaurantId, foodName);
        }
    }
}
