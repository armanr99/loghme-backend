package com.loghme.Server;

import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.Constants.Path;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.User.UserController;
import com.loghme.Restaurant.RestaurantController;
import com.loghme.Wallet.Exceptions.NotEnoughBalance;
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
            post(Path.Web.FINALIZE_CART, UserController.handleFinalizeCartPost);
        });

        app.exception(RestaurantDoesntExist.class, (e, ctx) -> ctx.status(404).html("404 Error: " + e.toString()));
        app.exception(RestaurantOutOfRange.class, (e, ctx) -> ctx.status(403).html("403 Error: Forbidden"));
        app.exception(WrongAmount.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
        app.exception(NumberFormatException.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
        app.exception(EmptyCartFinalize.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
        app.exception(NotEnoughBalance.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
    }
}
