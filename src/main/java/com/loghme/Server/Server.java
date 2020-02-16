package com.loghme.Server;

import com.loghme.Cart.Exceptions.DifferentRestaurant;
import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.Constants.Path;
import com.loghme.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.Restaurant.RestaurantRepository;
import com.loghme.User.UserController;
import com.loghme.Restaurant.RestaurantController;
import com.loghme.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.Wallet.Exceptions.WrongAmount;
import io.javalin.Javalin;
import com.loghme.Constants.ServerConfigs;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Server {
    private Javalin app;

    public Server() {
        app = Javalin.create();
    }

    public void start() {
        try {
            RestaurantRepository.getInstance().fetchData(ServerConfigs.DATA_URL);
        } catch (RestaurantAlreadyExists restaurantAlreadyExists) {
            System.out.println("Error in fetching data: " + restaurantAlreadyExists.toString());
            return;
        }
        handleApp();
    }

    private void handleApp() {
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
        app.exception(FoodDoesntExist.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
        app.exception(DifferentRestaurant.class, (e, ctx) -> ctx.status(400).html("400 Error: " + e.toString()));
    }
}
