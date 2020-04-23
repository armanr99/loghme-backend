package com.loghme.models.domain.User;

import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Restaurant.exceptions.*;
import com.loghme.models.services.RestaurantService;
import com.loghme.models.domain.Wallet.exceptions.NotEnoughBalance;
import com.loghme.models.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest {
    @After
    public void tearDown() {
        UserService.clearInstance();
        RestaurantService.clearInstance();
    }

    @Test (expected = EmptyCartFinalize.class)
    public void testFinalizeOrderEmptyCart() throws EmptyCartFinalize {
        try {
            UserService.getInstance().finalizeOrder();
        } catch (NotEnoughBalance | InvalidCount notEnoughBalance) {
            Assert.fail();
        }
    }

    @Test (expected = NotEnoughBalance.class)
    public void testFinalizeOrderNotEnoughBalance() throws NotEnoughBalance {
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "55000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantId\": \"1\"}";
        try {
            RestaurantService.getInstance().addRestaurant(testAddRestaurantJson);
            UserService.getInstance().addToCartStr(testAddToCart);
            UserService.getInstance().addToCartStr(testAddToCart);
            UserService.getInstance().finalizeOrder();
        } catch (RestaurantAlreadyExists | RestaurantDoesntExist | FoodDoesntExist | DifferentRestaurant | RestaurantOutOfRange | EmptyCartFinalize | InvalidCount | FoodAlreadyExistsInRestaurant exception) {
            Assert.fail();
        }
    }
}
