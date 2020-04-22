package com.loghme.models.domain.User;

import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantAlreadyExists;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.models.domain.Wallet.exceptions.NotEnoughBalance;
import com.loghme.repositories.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserRepositoryTest {
    @After
    public void tearDown() {
        UserRepository.clearInstance();
        RestaurantRepository.clearInstance();
    }

    @Test (expected = EmptyCartFinalize.class)
    public void testFinalizeOrderEmptyCart() throws EmptyCartFinalize {
        try {
            UserRepository.getInstance().finalizeOrder();
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
            RestaurantRepository.getInstance().addRestaurant(testAddRestaurantJson);
            UserRepository.getInstance().addToCart(testAddToCart);
            UserRepository.getInstance().addToCart(testAddToCart);
            UserRepository.getInstance().finalizeOrder();
        } catch (RestaurantAlreadyExists | RestaurantDoesntExist | FoodDoesntExist | DifferentRestaurant | RestaurantOutOfRange | EmptyCartFinalize | InvalidCount exception) {
            Assert.fail();
        }
    }
}
