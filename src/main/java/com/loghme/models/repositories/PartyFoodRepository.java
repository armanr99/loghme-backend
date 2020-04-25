package com.loghme.models.repositories;

import com.loghme.exceptions.FoodDoesntExist;
import com.loghme.models.DTOs.Food.PartyFoodInput;
import com.loghme.models.DTOs.Restaurant.FoodPartyRestaurantInput;
import com.loghme.models.domain.Food.PartyFood;

import java.util.ArrayList;

public class PartyFoodRepository {
    private ArrayList<PartyFood> partyFoods;
    private static PartyFoodRepository instance = null;

    public static PartyFoodRepository getInstance() {
        if (instance == null) {
            instance = new PartyFoodRepository();
        }
        return instance;
    }

    private PartyFoodRepository() {
        partyFoods = new ArrayList<>();
    }

    public void addRestaurantPartyFoods(FoodPartyRestaurantInput restaurantInput) {
        String restaurantId = restaurantInput.getId();
        ArrayList<PartyFoodInput> restaurantPartyFoodInputs = restaurantInput.getMenu();

        for (PartyFoodInput partyFoodInput : restaurantPartyFoodInputs) {
            addRestaurantPartyFood(restaurantId, partyFoodInput);
        }
    }

    private void addRestaurantPartyFood(String restaurantId, PartyFoodInput partyFoodInput) {
        PartyFood partyFood =
                new PartyFood(
                        partyFoodInput.getName(),
                        restaurantId,
                        partyFoodInput.getDescription(),
                        partyFoodInput.getImage(),
                        partyFoodInput.getPopularity(),
                        partyFoodInput.getPrice(),
                        partyFoodInput.getCount(),
                        partyFoodInput.getOldPrice());

        addPartyFood(partyFood);
    }

    private void addPartyFood(PartyFood partyFood) {
        if (!hasPartyFood(partyFood)) {
            partyFoods.add(partyFood);
        }
    }

    private boolean hasPartyFood(PartyFood partyFood) {
        try {
            getPartyFood(partyFood.getRestaurantId(), partyFood.getName());
            return true;
        } catch (FoodDoesntExist foodDoesntExist) {
            return false;
        }
    }

    public PartyFood getPartyFood(String restaurantId, String foodName) throws FoodDoesntExist {
        for (PartyFood partyFood : partyFoods) {
            if (restaurantId.equals(partyFood.getRestaurantId())
                    && foodName.equals(partyFood.getName())) {
                return partyFood;
            }
        }

        throw new FoodDoesntExist(foodName, restaurantId);
    }

    public ArrayList<PartyFood> getPartyFoods(String restaurantId) {
        ArrayList<PartyFood> restaurantPartyFoods = new ArrayList<>();

        for (PartyFood partyFood : partyFoods) {
            if (restaurantId.equals(partyFood.getRestaurantId())) {
                restaurantPartyFoods.add(partyFood);
            }
        }

        return restaurantPartyFoods;
    }

    public ArrayList<PartyFood> getPartyFoods() {
        return partyFoods;
    }

    public void deletePartyFoods() {
        partyFoods.clear();
    }
}
