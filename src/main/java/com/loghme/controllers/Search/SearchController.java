package com.loghme.controllers.Search;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantsResponse;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.SEARCH)
public class SearchController {
    @GetMapping("/restaurants")
    public RestaurantsResponse getRestaurants(
            @RequestParam(required = false, defaultValue = "") String restaurantName,
            @RequestParam(required = false, defaultValue = "") String foodName) throws SQLException {

        ArrayList<Restaurant> restaurants = RestaurantRepository.getInstance().searchRestaurants(restaurantName, foodName);
        return new RestaurantsResponse(restaurants);
    }
}
