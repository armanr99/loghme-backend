package com.loghme.User;

import com.loghme.Constants.Path;
import com.loghme.Restaurant.RestaurantRepository;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserController {
    public static Handler fetchUser = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("user", UserRepository.getInstance().getUser());
        ctx.render(Path.Template.USER, model);
    };

    public static Handler handleAddToCartPost = ctx -> {
        String foodName = ctx.formParam("foodName");
        String restaurantId = ctx.formParam("restaurantId");
        UserRepository.getInstance().addToCart(foodName, restaurantId);
        ctx.redirect(Path.Web.RESTAURANTS + "/" + restaurantId);
    };

    public static Handler fetchCart = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("cart", UserRepository.getInstance().getUser().getCartItemsList());
        ctx.render(Path.Template.CART, model);
    };

    public static Handler handleChargePost = ctx -> {
        double amount = Double.parseDouble(Objects.requireNonNull(ctx.formParam("amount")));
        UserRepository.getInstance().chargeUser(amount);
        ctx.redirect(Path.Web.USER);
    };

    public static Handler handleFinalizeCartPost = ctx -> {
        UserRepository.getInstance().finalizeOrder();
        ctx.redirect(Path.Web.USER);
    };
}
