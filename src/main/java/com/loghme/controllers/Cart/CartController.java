package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.requests.Cart.CartRequest;
import com.loghme.controllers.wrappers.responses.Cart.CartResponse;
import com.loghme.controllers.wrappers.responses.Order.OrdersResponse;
import com.loghme.models.domain.Cart.Cart;
import com.loghme.models.domain.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Order.Order;
import com.loghme.models.domain.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.domain.Wallet.exceptions.NotEnoughBalance;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(Path.Web.CART)
public class CartController {
    @GetMapping("")
    public CartResponse getCart() {
        Cart cart = UserRepository.getInstance().getUser().getCart();
        return new CartResponse(cart);
    }

    @PostMapping("")
    public CartResponse addToCart(@RequestBody CartRequest request) throws FoodDoesntExist, RestaurantOutOfRange, RestaurantDoesntExist, DifferentRestaurant, InvalidCount {
        UserRepository.getInstance().addToCart(request.getFoodName(), request.getRestaurantId());

        Cart cart = UserRepository.getInstance().getUser().getCart();

        return new CartResponse(cart);
    }

    @DeleteMapping("")
    public CartResponse removeFromCart(@RequestBody CartRequest request) throws CartItemDoesntExist {
        UserRepository.getInstance().removeFromCart(request.getFoodName(), request.getRestaurantId());

        Cart cart = UserRepository.getInstance().getUser().getCart();

        return new CartResponse(cart);
    }

    @PostMapping("/order")
    public OrdersResponse finalizeOrder() throws InvalidCount, EmptyCartFinalize, NotEnoughBalance {
        UserRepository.getInstance().finalizeOrder();

        ArrayList<Order> orders = UserRepository.getInstance().getUser().getOrdersList();

        return new OrdersResponse(orders);
    }
}