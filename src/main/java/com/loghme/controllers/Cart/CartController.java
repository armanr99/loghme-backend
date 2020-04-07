package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.Cart.CartWrapper;
import com.loghme.models.Cart.Cart;
import com.loghme.models.Cart.Exceptions.CartItemDoesntExist;
import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Food.Exceptions.InvalidCount;
import com.loghme.models.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Path.Web.CART)
public class CartController {
    @GetMapping("")
    public CartWrapper getCart() {
        Cart cart = UserRepository.getInstance().getUser().getCart();
        return new CartWrapper(cart);
    }

    @PostMapping("")
    public CartWrapper addToCart(@RequestParam("foodName") String foodName,
                                 @RequestParam("restaurantId") String restaurantId) throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist, DifferentRestaurant, InvalidCount {
        UserRepository.getInstance().addToCart(foodName, restaurantId);

        Cart cart = UserRepository.getInstance().getUser().getCart();

        return new CartWrapper(cart);
    }

    @DeleteMapping("")
    public CartWrapper removeFromCart(@RequestParam("foodName") String foodName,
                                      @RequestParam("restaurantId") String restaurantId) throws CartItemDoesntExist {
        UserRepository.getInstance().removeFromCart(foodName, restaurantId);

        Cart cart = UserRepository.getInstance().getUser().getCart();

        return new CartWrapper(cart);
    }
}