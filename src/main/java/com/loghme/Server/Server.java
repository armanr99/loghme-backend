package com.loghme.Server;

import com.loghme.Constants.Path;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.User.UserController;
import com.loghme.Restaurant.RestaurantController;
import io.javalin.Javalin;
import com.loghme.Constants.ServerConfigs;

import static io.javalin.apibuilder.ApiBuilder.get;

public class Server {
    private Javalin app;

    public Server() {
        app = Javalin.create();
    }

    public void start() {
        app.start(ServerConfigs.PORT);

        app.routes(() -> {
            get(Path.Web.USER, UserController.fetchUser);
            get(Path.Web.RESTAURANTS, RestaurantController.fetchAllRestaurants);
            get(Path.Web.ONE_RESTAURANT, RestaurantController.fetchOneRestaurant);
        });

        app.exception(RestaurantDoesntExist.class, (e, ctx) -> ctx.status(404)).error(404, RestaurantController.restaurantNotFound);
    }
}
