package com.loghme.controllers.FoodParty;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.Restaurant.RestaurantsWrapper;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.FOOD_PARTY)
public class FoodPartyController {
    @GetMapping("")
    public RestaurantsWrapper getFoodPartyRestaurants() {
        ArrayList<Restaurant> foodPartyRestaurants = RestaurantRepository.getInstance().getFoodPartyRestaurants();
        return new RestaurantsWrapper(foodPartyRestaurants);
    }
}
