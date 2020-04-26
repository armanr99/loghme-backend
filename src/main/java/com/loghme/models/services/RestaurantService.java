package com.loghme.models.services;

import com.google.gson.*;
import com.loghme.configs.ServerConfigs;
import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.exceptions.RestaurantOutOfRange;
import com.loghme.models.DTOs.Food.FoodInput;
import com.loghme.models.DTOs.Food.PartyFoodInput;
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

import java.sql.SQLException;
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

    public void fetchRestaurants(String sourceURL) throws JsonSyntaxException, SQLException {
        String restaurantsData = HTTPRequester.get(sourceURL);
        RestaurantInput[] restaurantInputs =
                gson.fromJson(restaurantsData, RestaurantInput[].class);

        addRestaurants(restaurantInputs);
        addFoods(restaurantInputs);
    }

    private void addRestaurants(RestaurantInput[] restaurantInputs) throws SQLException {
        ArrayList<Restaurant> restaurants = makeRestaurantInstances(restaurantInputs);
        RestaurantRepository.getInstance().addRestaurants(restaurants);
    }

    private ArrayList<Restaurant> makeRestaurantInstances(RestaurantInput[] restaurantInputs) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        for (RestaurantInput restaurantInput : restaurantInputs) {
            restaurants.add(makeRestaurantInstance(restaurantInput));
        }

        return restaurants;
    }

    private void addFoods(RestaurantInput[] restaurantInputs) throws SQLException {
        ArrayList<Food> foods = makeFoodInstances(restaurantInputs);
        FoodRepository.getInstance().addFoods(foods);
    }

    private ArrayList<Food> makeFoodInstances(RestaurantInput[] restaurantInputs) {
        ArrayList<Food> foodInstances = new ArrayList<>();

        for (RestaurantInput restaurantInput : restaurantInputs) {
            foodInstances.addAll(makeFoodInstances(restaurantInput));
        }

        return foodInstances;
    }

    private ArrayList<Food> makeFoodInstances(RestaurantInput restaurantInput) {
        ArrayList<Food> foodInstances = new ArrayList<>();
        String restaurantId = restaurantInput.getId();

        for (FoodInput foodInput : restaurantInput.getMenu()) {
            foodInstances.add(makeFoodInstance(restaurantId, foodInput));
        }

        return foodInstances;
    }

    private Food makeFoodInstance(String restaurantId, FoodInput foodInput) {
        return new Food(
                foodInput.getName(),
                restaurantId,
                foodInput.getDescription(),
                foodInput.getImage(),
                foodInput.getPopularity(),
                foodInput.getPrice());
    }

    private Restaurant makeRestaurantInstance(RestaurantInput restaurantInput) {
        return new Restaurant(
                restaurantInput.getId(),
                restaurantInput.getName(),
                restaurantInput.getLogo(),
                restaurantInput.getLocation());
    }

    public Restaurant getRestaurantInstanceIfInRange(
            String restaurantId, Location source, double distance)
            throws RestaurantDoesntExist, RestaurantOutOfRange, SQLException {
        Restaurant restaurant = RestaurantRepository.getInstance().getRestaurant(restaurantId);

        if (isRestaurantInRange(restaurant, source, distance)) {
            return restaurant;
        } else {
            throw new RestaurantOutOfRange();
        }
    }

    public ArrayList<Restaurant> getRestaurantsWithinDistance(Location source, double distance)
            throws SQLException {
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

    public ArrayList<PartyFood> getPartyFoods() throws SQLException {
        return PartyFoodRepository.getInstance().getPartyFoods();
    }

    public long getRemainingFoodPartySeconds() {
        return FoodPartyScheduler.getInstance().getRemainingSeconds();
    }

    public void fetchPartyFoods() throws JsonSyntaxException, SQLException {
        String restaurantsData = HTTPRequester.get(ServerConfigs.FOOD_PARTY_URL);
        FoodPartyRestaurantInput[] foodPartyRestaurantInputs =
                gson.fromJson(restaurantsData, FoodPartyRestaurantInput[].class);

        addFoodPartyRestaurants(foodPartyRestaurantInputs);
        addPartyFoods(foodPartyRestaurantInputs);
    }

    private void addFoodPartyRestaurants(FoodPartyRestaurantInput[] foodPartyRestaurantInputs)
            throws SQLException {
        ArrayList<Restaurant> restaurants = makeRestaurantInstances(foodPartyRestaurantInputs);
        RestaurantRepository.getInstance().addRestaurants(restaurants);
    }

    private ArrayList<Restaurant> makeRestaurantInstances(
            FoodPartyRestaurantInput[] foodPartyRestaurantInputs) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        for (FoodPartyRestaurantInput foodPartyRestaurantInput : foodPartyRestaurantInputs) {
            restaurants.add(makeRestaurantInstance(foodPartyRestaurantInput));
        }

        return restaurants;
    }

    private void addPartyFoods(FoodPartyRestaurantInput[] foodPartyRestaurantInputs)
            throws SQLException {
        ArrayList<PartyFood> partyFoods = makePartyFoodInstances(foodPartyRestaurantInputs);
        PartyFoodRepository.getInstance().addPartyFoods(partyFoods);
    }

    private ArrayList<PartyFood> makePartyFoodInstances(
            FoodPartyRestaurantInput[] foodPartyRestaurantInputs) {
        ArrayList<PartyFood> foodInstances = new ArrayList<>();

        for (FoodPartyRestaurantInput restaurantInput : foodPartyRestaurantInputs) {
            foodInstances.addAll(makePartyFoodInstances(restaurantInput));
        }

        return foodInstances;
    }

    private ArrayList<PartyFood> makePartyFoodInstances(FoodPartyRestaurantInput restaurantInput) {
        ArrayList<PartyFood> foodInstances = new ArrayList<>();
        String restaurantId = restaurantInput.getId();

        for (PartyFoodInput foodInput : restaurantInput.getMenu()) {
            foodInstances.add(makePartyFoodInstance(restaurantId, foodInput));
        }

        return foodInstances;
    }

    private PartyFood makePartyFoodInstance(String restaurantId, PartyFoodInput foodInput) {
        return new PartyFood(
                foodInput.getName(),
                restaurantId,
                foodInput.getDescription(),
                foodInput.getImage(),
                foodInput.getPopularity(),
                foodInput.getPrice(),
                foodInput.getCount(),
                foodInput.getOldPrice());
    }

    private Restaurant makeRestaurantInstance(FoodPartyRestaurantInput foodPartyRestaurantInputs) {
        return new Restaurant(
                foodPartyRestaurantInputs.getId(),
                foodPartyRestaurantInputs.getName(),
                foodPartyRestaurantInputs.getLogo(),
                foodPartyRestaurantInputs.getLocation());
    }

    public void deletePartyFoods() throws SQLException {
        PartyFoodRepository.getInstance().deletePartyFoods();
    }

    public Food getFood(String restaurantId, String foodName) throws FoodDoesntExist, SQLException {
        try {
            return PartyFoodRepository.getInstance().getPartyFood(restaurantId, foodName);
        } catch (FoodDoesntExist foodDoesntExist) {
            return FoodRepository.getInstance().getFood(restaurantId, foodName);
        }
    }
}
