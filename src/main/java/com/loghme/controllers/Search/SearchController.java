package com.loghme.controllers.Search;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantsResponse;
import com.loghme.models.domain.Restaurant.Restaurant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.SEARCH)
public class SearchController {
    @GetMapping("/restaurants")
    public RestaurantsResponse getRestaurants(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String foodName) {

        System.out.println("name: " + name);
        System.out.println("foodName: " + foodName);

        ArrayList<Restaurant> todo = new ArrayList<>();
        return new RestaurantsResponse(todo);
    }
}
