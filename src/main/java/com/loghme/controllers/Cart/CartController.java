package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.Cart.CartWrapper;
import com.loghme.models.Cart.Cart;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.Web.CART)
public class CartController {
    @GetMapping("")
    public CartWrapper getUser() {
        Cart cart = UserRepository.getInstance().getUser().getCart();
        return new CartWrapper(cart);
    }
}