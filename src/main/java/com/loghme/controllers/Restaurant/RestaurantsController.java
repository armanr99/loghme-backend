package com.loghme.controllers.Restaurant;

import com.loghme.configs.Configs;
import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.Restaurant.RestaurantWrapper;
import com.loghme.controllers.wrappers.Restaurant.RestaurantsWrapper;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.Restaurant.Restaurant;
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
    public RestaurantsWrapper getRestaurants() {
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        ArrayList<Restaurant> restaurants = RestaurantRepository.getInstance().getRestaurantsWithinDistance(userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantsWrapper(restaurants);
    }

    @GetMapping("{id}")
    public RestaurantWrapper getRestaurant(@PathVariable(value = "id") String id) throws RestaurantOutOfRange, RestaurantDoesntExist {
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
        Restaurant restaurant = restaurantRepository.getRestaurantInstanceIfInRange(id, userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantWrapper(restaurant);
    }
}
