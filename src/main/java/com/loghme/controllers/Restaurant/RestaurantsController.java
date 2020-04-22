package com.loghme.controllers.Restaurant;

import com.loghme.configs.Configs;
import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantResponse;
import com.loghme.controllers.wrappers.responses.Restaurant.RestaurantsResponse;
import com.loghme.models.domain.Location.Location;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.RESTAURANTS)
public class RestaurantsController {
    @GetMapping("")
    public RestaurantsResponse getRestaurants() {
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        ArrayList<Restaurant> restaurants = RestaurantRepository.getInstance().getRestaurantsWithinDistance(userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantsResponse(restaurants);
    }

    @GetMapping("{id}")
    public RestaurantResponse getRestaurant(@PathVariable(value = "id") String id) throws RestaurantOutOfRange, RestaurantDoesntExist {
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
        Restaurant restaurant = restaurantRepository.getRestaurantInstanceIfInRange(id, userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantResponse(restaurant);
    }
}
