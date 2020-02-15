package com.loghme.Restaurant;

import com.loghme.Constants.Configs;
import com.loghme.Constants.Path;
import com.loghme.Constants.RestaurantControllerConfig;
import com.loghme.Location.Location;
import com.loghme.User.UserRepository;
import io.javalin.http.ErrorHandler;
import io.javalin.http.Handler;

import java.util.HashMap;

public class RestaurantController {

    public static Handler fetchAllRestaurants = ctx -> {
        String dataURL = RestaurantControllerConfig.DATA_URL;
        HashMap<String, Object> model = new HashMap<>();
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        model.put("restaurants", RestaurantRepository.getInstance(dataURL).getRestaurantsWithinDistance(userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE));
        ctx.render(Path.Template.RESTAURANTS_ALL, model);
    };

    public static Handler fetchOneRestaurant = ctx -> {
        String dataURL = RestaurantControllerConfig.DATA_URL;
        HashMap<String, Object> model = new HashMap<>();
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        RestaurantRepository restaurantRepository = RestaurantRepository.getInstance(dataURL);
        Restaurant restaurant = restaurantRepository.getRestaurantInstanceIfInRange(ctx.pathParam("id"), userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        model.put("restaurant", restaurant);
        ctx.render(Path.Template.RESTAURANTS_ONE, model);
    };

    public static ErrorHandler restaurantNotFound = ctx -> ctx.render(Path.Template.RESTAURANTS_NOT_FOUND);
}
