package com.loghme.models.Restaurant;

import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.repositories.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class RestaurantRepositoryTest {

    @After
    public void tearDown() {
        RestaurantRepository.clearInstance();
        UserRepository.clearInstance();
    }

    @Test (expected = RestaurantOutOfRange.class)
    public void testGetRestaurantNotInRange() throws RestaurantOutOfRange {
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 190, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        try {
            RestaurantRepository.getInstance().addRestaurant(testAddRestaurantJson);
            Location userLocation = new Location(0, 0);
            RestaurantRepository.getInstance().getRestaurantInstanceIfInRange("1", userLocation, 170);
        } catch (RestaurantAlreadyExists | RestaurantDoesntExist exception) {
            Assert.fail();
        }
    }

    @Test (expected = RestaurantDoesntExist.class)
    public void testGetRestaurantDoesntExist() throws RestaurantDoesntExist {
        try {
            Location userLocation = new Location(0, 0);
            RestaurantRepository.getInstance().getRestaurantInstanceIfInRange("1", userLocation, 170);
        } catch (RestaurantOutOfRange exception) {
            Assert.fail();
        }
    }
}