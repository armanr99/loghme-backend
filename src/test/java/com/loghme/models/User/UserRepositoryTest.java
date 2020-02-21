package com.loghme.models.User;

import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;
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
        } catch (NotEnoughBalance notEnoughBalance) {
            Assert.fail();
        }
    }

    @Test (expected = NotEnoughBalance.class)
    public void testFinalizeOrderNotEnoughBalance() throws NotEnoughBalance {
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "55000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantID\": \"1\"}";
        try {
            RestaurantRepository.getInstance().addRestaurant(testAddRestaurantJson);
            UserRepository.getInstance().addToCart(testAddToCart);
            UserRepository.getInstance().addToCart(testAddToCart);
            UserRepository.getInstance().finalizeOrder();
        } catch (RestaurantAlreadyExists | RestaurantDoesntExist | FoodDoesntExist | DifferentRestaurant | RestaurantOutOfRange | EmptyCartFinalize exception) {
            Assert.fail();
        }
    }
}
