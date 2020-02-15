package com.loghme.Server;

import com.loghme.Constants.Path;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.User.UserController;
import com.loghme.Restaurant.RestaurantController;
import com.loghme.User.UserRepository;
import com.loghme.Wallet.Exceptions.WrongAmount;
import io.javalin.Javalin;
import com.loghme.Constants.ServerConfigs;
import org.apache.http.HttpStatus;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

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
            get(Path.Web.CART, UserController.fetchCart);
            post(Path.Web.FOOD, UserController.handleAddToCartPost);
            post(Path.Web.WALLET, UserController.handleChargePost);
        });

        app.exception(RestaurantDoesntExist.class, (e, ctx) -> ctx.status(HttpStatus.SC_NOT_FOUND)).error(HttpStatus.SC_NOT_FOUND, RestaurantController.restaurantNotFound);
        app.exception(RestaurantOutOfRange.class, (e, ctx) -> ctx.status(HttpStatus.SC_FORBIDDEN)).error(HttpStatus.SC_FORBIDDEN, ctx -> ctx.html("403 Forbidden"));
        app.exception(WrongAmount.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
        app.exception(NumberFormatException.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));


    }
}
