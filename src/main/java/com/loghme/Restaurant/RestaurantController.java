package com.loghme.Restaurant;

import com.loghme.Constants.Path;
import com.loghme.Constants.RestaurantControllerConfig;
import com.loghme.Location.Location;
import com.loghme.User.UserRepository;
import io.javalin.http.Handler;

import java.util.HashMap;

public class RestaurantController {

    public static Handler fetchAllRestaurants = ctx -> {
        HashMap<String, Object> model = new HashMap<>();
        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        model.put("restaurants", RestaurantRepository.getInstance().getRestaurantsWithinDistance(userLocation, RestaurantControllerConfig.VISIBLE_RESTAURANTS_DISTANCE));
        ctx.render(Path.Template.RESTAURANTS_ALL, model);
    };

    public static Handler fetchOneRestaurant = ctx -> {
        HashMap<String, Object> model = new HashMap<>();
        model.put("restaurant", RestaurantRepository.getInstance().getRestaurantInstance(ctx.pathParam("id")));
        ctx.render(Path.Template.RESTAURANTS_ONE, model);
    };

}
