package com.loghme.controllers.FoodParty;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.FoodParty.FoodPartyResponse;
import com.loghme.controllers.wrappers.responses.FoodParty.RemainingTimeResponse;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.RestaurantRepository;
import com.loghme.schedulers.FoodPartyScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.FOOD_PARTY)
public class FoodPartyController {
    @GetMapping("")
    public FoodPartyResponse getPartyFoods() {
        ArrayList<Restaurant> foodPartyRestaurants = RestaurantRepository.getInstance().getFoodPartyRestaurants();
        return new FoodPartyResponse(foodPartyRestaurants);
    }

    @GetMapping(Path.Web.FOOD_PARTY_TIME)
    public RemainingTimeResponse getFoodPartyTime() {
        long remainingSeconds = FoodPartyScheduler.getInstance().getRemainingSeconds();
        return new RemainingTimeResponse(remainingSeconds);
    }
}
