package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.requests.Cart.CartRequest;
import com.loghme.controllers.wrappers.responses.Cart.CartResponse;
import com.loghme.controllers.wrappers.responses.User.UserResponse;
import com.loghme.models.Cart.Cart;
import com.loghme.models.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.Food.exceptions.InvalidCount;
import com.loghme.models.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.User.User;
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
    public UserResponse addToCart(@RequestBody CartRequest request) throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist, DifferentRestaurant, InvalidCount {
        UserRepository.getInstance().addToCart(request.getFoodName(), request.getRestaurantId());

        User user = UserRepository.getInstance().getUser();

        return new UserResponse(user);
    }

    @DeleteMapping("")
    public UserResponse removeFromCart(@RequestBody CartRequest request) throws CartItemDoesntExist {
        UserRepository.getInstance().removeFromCart(request.getFoodName(), request.getRestaurantId());

        User user = UserRepository.getInstance().getUser();

        return new UserResponse(user);
    }

    @PostMapping("/order")
    public UserResponse finalizeOrder() throws InvalidCount, EmptyCartFinalize, NotEnoughBalance {
        UserRepository.getInstance().finalizeOrder();

        User user = UserRepository.getInstance().getUser();

        return new UserResponse(user);
    }
}