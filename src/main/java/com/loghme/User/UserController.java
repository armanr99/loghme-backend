package com.loghme.User;

import com.loghme.Constants.Path;
import com.loghme.Constants.RestaurantControllerConfig;
import com.loghme.Restaurant.RestaurantRepository;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static Handler fetchUser = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("user", UserRepository.getInstance().getUser());
        ctx.render(Path.Template.USER, model);
    };

    public static Handler handleAddToCartPost = ctx -> {
        String foodName = ctx.formParam("foodName");
        String restaurantId = ctx.formParam("restaurantId");
        String dataURL = RestaurantControllerConfig.DATA_URL;
        UserRepository.getInstance().addToCart(foodName, restaurantId, RestaurantRepository.getInstance(dataURL));
        ctx.redirect(Path.Web.RESTAURANTS + "/" + restaurantId);
    };

    public static Handler fetchCart = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("cart", UserRepository.getInstance().getUser().getCartItemsList());
        ctx.render(Path.Template.CART, model);
    };
}
