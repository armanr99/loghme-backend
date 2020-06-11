package com.loghme.controllers.FoodParty;

import com.loghme.configs.PathConfigs;
import com.loghme.controllers.DTOs.responses.FoodParty.FoodPartyResponse;
import com.loghme.controllers.DTOs.responses.FoodParty.RemainingTimeResponse;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Food.PartyFood;
import com.loghme.models.services.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping(PathConfigs.Web.FOOD_PARTY)
public class FoodPartyController {
    @GetMapping("")
    public FoodPartyResponse getPartyFoods() throws RestaurantDoesntExist, SQLException {
        ArrayList<PartyFood> partyFoods = RestaurantService.getInstance().getPartyFoods();
        return new FoodPartyResponse(partyFoods);
    }

    @GetMapping(PathConfigs.Web.FOOD_PARTY_TIME)
    public RemainingTimeResponse getFoodPartyTime() {
        long remainingSeconds = RestaurantService.getInstance().getRemainingFoodPartySeconds();
        return new RemainingTimeResponse(remainingSeconds);
    }
}
