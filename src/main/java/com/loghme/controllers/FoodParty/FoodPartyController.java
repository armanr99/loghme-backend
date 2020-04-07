package com.loghme.controllers.FoodParty;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantsResponse;
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
    public RestaurantsResponse getFoodPartyRestaurants() {
        ArrayList<Restaurant> foodPartyRestaurants = RestaurantRepository.getInstance().getFoodPartyRestaurants();
        return new RestaurantsResponse(foodPartyRestaurants);
    }
}
