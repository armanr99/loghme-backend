package com.loghme.Loghme;


import com.loghme.Food.Food;
import com.loghme.Restaurant.Restaurant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            Field restaurantsField = loghmeClass.getDeclaredField("restaurants");
            restaurantsField.setAccessible(true);
            HashMap<String, Restaurant> restaurants = (HashMap<String, Restaurant>) restaurantsField.get(loghmeTest);

            Assert.assertTrue(restaurants.containsKey("Hesturan"));
            Restaurant hesturan = restaurants.get("Hesturan");
            Assert.assertEquals(hesturan.getName(), "Hesturan");
            Assert.assertEquals(hesturan.getDescription(), "luxury");
            Assert.assertEquals(hesturan.getLocation().getX(), 1, 1e-9);

            Food gheime = hesturan.getFood("Gheime");
            Assert.assertNotNull(gheime);
            Assert.assertEquals(gheime.getName(), "Gheime");
            Assert.assertEquals(gheime.getDescription(), "it's yummy!");
            Assert.assertEquals(gheime.getPopularity(), 0.8, 1e-9);
            Assert.assertEquals(gheime.getPrice(), 20000, 1e-9);
        } catch (RestaurantAlreadyExists restaurantAlreadyExists) {
            Assert.fail();
        }
    }

    @Test
    public void testAddFood() {
        String testAddRestaurantJson = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddFoodJson = "{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"restaurantName\": \"Hesturan\", \"price\": 20000}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            loghmeTest.addFood(testAddFoodJson);

            Field restaurantsField = loghmeClass.getDeclaredField("restaurants");
            restaurantsField.setAccessible(true);
            HashMap<String, Restaurant> restaurants = (HashMap<String, Restaurant>) restaurantsField.get(loghmeTest);

            Restaurant hesturan = restaurants.get("Hesturan");
            Food gheime = hesturan.getFood("Gheime");
            Assert.assertNotNull(gheime);
            Assert.assertEquals(gheime.getName(), "Gheime");
            Assert.assertEquals(gheime.getDescription(), "it's yummy!");
            Assert.assertEquals(gheime.getPopularity(), 0.8, 1e-9);
            Assert.assertEquals(gheime.getPrice(), 20000, 1e-9);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetRestaurants() {
        String testAddRestaurantJson1 = "{\"name\": \"Bonab\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddRestaurantJson2 = "{\"name\": \"Hani\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson1);
            loghmeTest.addRestaurant(testAddRestaurantJson2);

            List<String> restaurants = loghmeTest.getRestaurants();
            Assert.assertTrue(restaurants.contains("Bonab"));
            Assert.assertTrue(restaurants.contains("Hani"));
        } catch(Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetRestaurant() {
        String testAddRestaurantJson = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testGetRestaurantJson = "{\"name\": \"Hesturan\"}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);

            String restaurantJson = loghmeTest.getRestaurant(testGetRestaurantJson);
            JSONAssert.assertEquals(testAddRestaurantJson, restaurantJson, JSONCompareMode.LENIENT);

        } catch(Exception exception) {
                Assert.fail();
        }
    }
}
