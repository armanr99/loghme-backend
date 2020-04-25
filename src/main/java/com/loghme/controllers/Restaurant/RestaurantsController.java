package com.loghme.controllers.Restaurant;

import com.loghme.configs.Configs;
import com.loghme.configs.Path;
import com.loghme.configs.UserConfigs;
import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantResponse;
import com.loghme.controllers.DTOs.responses.Restaurant.RestaurantsResponse;
import com.loghme.exceptions.UserDoesntExist;
import com.loghme.models.domain.Location.Location;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.exceptions.RestaurantOutOfRange;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.services.RestaurantService;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.RESTAURANTS)
public class RestaurantsController {
    @GetMapping("")
    public RestaurantsResponse getRestaurants() throws UserDoesntExist {
        int userId = UserConfigs.DEFAULT_ID;
        Location userLocation = UserService.getInstance().getUser(userId).getLocation();
        ArrayList<Restaurant> restaurants =
                RestaurantService.getInstance()
                        .getRestaurantsWithinDistance(
                                userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantsResponse(restaurants);
    }

    @GetMapping("{id}")
    public RestaurantResponse getRestaurant(@PathVariable(value = "id") String id)
            throws RestaurantOutOfRange, RestaurantDoesntExist, UserDoesntExist {
        int userId = UserConfigs.DEFAULT_ID;
        Location userLocation = UserService.getInstance().getUser(userId).getLocation();
        RestaurantService restaurantService = RestaurantService.getInstance();
        Restaurant restaurant =
                restaurantService.getRestaurantInstanceIfInRange(
                        id, userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        return new RestaurantResponse(restaurant);
    }
}
