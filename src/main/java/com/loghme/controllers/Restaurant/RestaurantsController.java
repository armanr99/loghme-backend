package com.loghme.controllers.Restaurant;

import com.loghme.configs.Configs;
import com.loghme.configs.Path;
import com.loghme.controllers.Restaurant.wrappers.RestaurantsWrapper;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.RESTAURANTS)
public class RestaurantsController {
    @GetMapping("")
    public RestaurantsWrapper getRestaurants() {
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        ArrayList<Restaurant> restaurants = RestaurantRepository.getInstance().getRestaurantsWithinDistance(userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantsWrapper(restaurants);
    }
}
