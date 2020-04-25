package com.loghme.models.repositories;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.DTOs.Food.FoodInput;
import com.loghme.models.DTOs.Restaurant.RestaurantInput;
import com.loghme.models.domain.Food.Food;

import java.util.ArrayList;

public class FoodRepository {
    private ArrayList<Food> foods;
    private static FoodRepository instance = null;

    public static FoodRepository getInstance() {
        if (instance == null) {
            instance = new FoodRepository();
        }
        return instance;
    }

    private FoodRepository() {
        foods = new ArrayList<>();
    }

    void addRestaurantFoods(RestaurantInput restaurantInput) {
        String restaurantId = restaurantInput.getId();
        ArrayList<FoodInput> restaurantFoodInputs = restaurantInput.getMenu();

        for (FoodInput foodInput : restaurantFoodInputs) {
            addRestaurantFood(restaurantId, foodInput);
        }
    }

    private void addRestaurantFood(String restaurantId, FoodInput foodInput) {
        Food food =
                new Food(
                        foodInput.getName(),
                        restaurantId,
                        foodInput.getDescription(),
                        foodInput.getImage(),
                        foodInput.getPopularity(),
                        foodInput.getPrice());

        addFood(food);
    }

    private void addFood(Food food) {
        if (!hasFood(food)) {
            foods.add(food);
        }
    }

    private boolean hasFood(Food food) {
        try {
            getFood(food.getRestaurantId(), food.getName());
            return true;
        } catch (FoodDoesntExist foodDoesntExist) {
            return false;
        }
    }

    public Food getFood(String restaurantId, String foodName) throws FoodDoesntExist {
        for (Food food : foods) {
            if (restaurantId.equals(food.getRestaurantId()) && foodName.equals(food.getName())) {
                return food;
            }
        }

        throw new FoodDoesntExist(foodName, restaurantId);
    }

    public ArrayList<Food> getFoods(String restaurantId) {
        ArrayList<Food> restaurantFoods = new ArrayList<>();

        for (Food food : foods) {
            if (restaurantId.equals(food.getRestaurantId())) restaurantFoods.add(food);
        }

        return restaurantFoods;
    }
}
