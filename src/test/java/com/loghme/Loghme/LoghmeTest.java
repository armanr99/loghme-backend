package com.loghme.Loghme;


import com.loghme.Food.Food;
import com.loghme.Restaurant.Restaurant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

public class LoghmeTest {

    private Loghme loghmeTest;
    private Class<?> loghmeClass;

    @Before
    public void setup() {
        loghmeTest = new Loghme();

        loghmeClass = loghmeTest.getClass();
    }

    @Test
    public void testAddRestaurant() throws NoSuchFieldException, IllegalAccessException {
        String testAddRestaurantJson = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it’s yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it’s delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            Field restaurantsField = loghmeClass.getDeclaredField("restaurants");
            restaurantsField.setAccessible(true);
            Object restaurantsObject = restaurantsField.get(loghmeTest);
            HashMap<String, Restaurant> restaurants = (HashMap<String, Restaurant>) restaurantsField.get(loghmeTest);
            Assert.assertTrue(restaurants.containsKey("Hesturan"));
            Restaurant hesturan = restaurants.get("Hesturan");
            Assert.assertEquals(hesturan.getName(), "Hesturan");
            Assert.assertEquals(hesturan.getDescription(), "luxury");
            Assert.assertEquals(hesturan.getLocation().getX(), 1, 1e-9);
            HashMap<String, Food> foods = hesturan.getFoods();
            Assert.assertNull(foods);
            Assert.assertNotNull(hesturan.getFood("Gheime"));
            Food gheime = hesturan.getFood("Gheime");
            Assert.assertEquals(gheime.getName(), "Gheime");
            Assert.assertEquals(gheime.getDescription(), "its's yummy!");
            Assert.assertEquals(gheime.getPopularity(), 0.8, 1e-9);
            Assert.assertEquals(gheime.getPrice(), 20000, 1e-9);
        } catch (RestaurantAlreadyExists restaurantAlreadyExists) {
            Assert.fail();
        }
    }
}
