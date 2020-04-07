package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.Cart.CartResponse;
import com.loghme.models.Cart.Cart;
import com.loghme.models.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.Food.exceptions.InvalidCount;
import com.loghme.models.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.Wallet.exceptions.NotEnoughBalance;
import com.loghme.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Path.Web.CART)
public class CartController {
    @GetMapping("")
    public CartResponse getCart() {
        Cart cart = UserRepository.getInstance().getUser().getCart();
        return new CartResponse(cart);
    }

    @PostMapping("")
    public CartResponse addToCart(@RequestParam("foodName") String foodName,
                                  @RequestParam("restaurantId") String restaurantId) throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist, DifferentRestaurant, InvalidCount {
        UserRepository.getInstance().addToCart(foodName, restaurantId);

        Cart cart = UserRepository.getInstance().getUser().getCart();

        return new CartResponse(cart);
    }

    @DeleteMapping("")
    public CartResponse removeFromCart(@RequestParam("foodName") String foodName,
                                       @RequestParam("restaurantId") String restaurantId) throws CartItemDoesntExist {
        UserRepository.getInstance().removeFromCart(foodName, restaurantId);

        Cart cart = UserRepository.getInstance().getUser().getCart();

        return new CartResponse(cart);
    }

    @PostMapping("/order")
    public ResponseEntity finalizeOrder() throws InvalidCount, EmptyCartFinalize, NotEnoughBalance {
        UserRepository.getInstance().finalizeOrder();
        return new ResponseEntity(HttpStatus.OK);
    }
}