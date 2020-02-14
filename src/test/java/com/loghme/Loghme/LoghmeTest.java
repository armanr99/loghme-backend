package com.loghme.Loghme;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loghme.Cart.DifferentRestaurant;
import com.loghme.CartItem.CartItem;
import com.loghme.Constants.Fields;
import com.loghme.Constants.GeneralConstants;
import com.loghme.Food.Food;
import com.loghme.Restaurant.*;
import com.loghme.User.User;
import com.loghme.User.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class LoghmeTest {

    private Loghme loghmeTest;

    @Before
    public void setup() {
        loghmeTest = new Loghme();
    }

    @After
    public void teardown() {
        loghmeTest = null;
        UserRepository.clearInstance();
        RestaurantRepository.clearInstance();
    }

    @Test
    public void testAddRestaurant() throws NoSuchFieldException, IllegalAccessException {
        String testAddRestaurantJson = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);

            Restaurant hesturan = RestaurantRepository.getInstance().getRestaurantInstance("Hesturan");
            Assert.assertEquals(hesturan.getName(), "Hesturan");
            Assert.assertEquals(hesturan.getDescription(), "luxury");
            Assert.assertEquals(hesturan.getLocation().getX(), 1, 1e-9);
            Assert.assertEquals(hesturan.getLocation().getY(), 3, 1e-9);

            Food gheime = hesturan.getFood("Gheime");
            Assert.assertNotNull(gheime);
            Assert.assertEquals(gheime.getName(), "Gheime");
            Assert.assertEquals(gheime.getDescription(), "it's yummy!");
            Assert.assertEquals(gheime.getPopularity(), 0.8, 1e-9);
            Assert.assertEquals(gheime.getPrice(), 20000, 1e-9);
        } catch (RestaurantAlreadyExists | RestaurantDoesntExist restaurantAlreadyExists) {
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

            Restaurant hesturan = RestaurantRepository.getInstance().getRestaurantInstance("Hesturan");
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

    @Test
    public void testGetFood() {
        String testAddRestaurantJson = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testGetFoodsJson = "{\"foodName\": \"Kabab\", \"restaurantName\": \"Hesturan\"}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);

            String foodJson = loghmeTest.getFood(testGetFoodsJson);
            String testFoodJson = "{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}";

            JSONAssert.assertEquals(testFoodJson, foodJson, JSONCompareMode.LENIENT);
        } catch(Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testAddToCartCorrect() {
        String testAddRestaurantJson = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantName\": \"Hesturan\"}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            loghmeTest.addToCart(testAddToCart);

            User user = UserRepository.getInstance().getUser();

            List<CartItem> userCartItems = user.getCartItemsList();
            Assert.assertEquals(1, userCartItems.size());
            Assert.assertEquals("Kabab", userCartItems.get(0).getFoodName());
            Assert.assertEquals("Hesturan", userCartItems.get(0).getRestaurantName());

        } catch(Exception exception) {
            Assert.fail();
        }
    }

   @Test(expected = DifferentRestaurant.class)
   public void testAddToCartDifferentRestaurant() throws DifferentRestaurant {
       String testAddRestaurantJson1 = "{\"name\": \"Bonab\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
               "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
               "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
               "30000}]}";
       String testAddRestaurantJson2 = "{\"name\": \"Hani\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
               "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
               "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
               "30000}]}";
       String testAddToCart1 = "{\"foodName\": \"Kabab\", \"restaurantName\": \"Bonab\"}";
       String testAddToCart2 = "{\"foodName\": \"Kabab\", \"restaurantName\": \"Hani\"}";

       try {
           loghmeTest.addRestaurant(testAddRestaurantJson1);
           loghmeTest.addRestaurant(testAddRestaurantJson2);
           loghmeTest.addToCart(testAddToCart1);
           loghmeTest.addToCart(testAddToCart2);
       } catch(RestaurantAlreadyExists | RestaurantDoesntExist | FoodDoesntExist exception) {
           Assert.fail();
       }
   }

   @Test
   public void testGetCart() {
        Gson gson = new Gson();
        String testAddRestaurantJson = "{\"name\": \"Bonab\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
               "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
               "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
               "30000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantName\": \"Bonab\"}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            loghmeTest.addToCart(testAddToCart);
            String cartJson = loghmeTest.getCart();

            JsonObject cartObject = gson.fromJson(cartJson, JsonObject.class);

            JsonArray cartArrays = cartObject.get("items").getAsJsonArray();
            JsonObject cartItemObject = cartArrays.get(0).getAsJsonObject();

            JsonElement foodNameElement = cartItemObject.get(Fields.FOOD_NAME);
            String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();
            Assert.assertEquals(foodName, "Kabab");

            JsonElement restaurantNameElement = cartItemObject.get(Fields.RESTAURANT_NAME);
            String restaurantName = restaurantNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantNameElement.getAsString();
            Assert.assertEquals(restaurantName, "Bonab");

            JsonElement countElement = cartItemObject.get("count");
            int count = countElement.getAsInt();
            Assert.assertEquals(1, count);

        } catch(Exception exception) {
           Assert.fail();
        }
    }

    @Test
    public void testFinalizeOrder() {
        Gson gson = new Gson();
        String testAddRestaurantJson = "{\"name\": \"Bonab\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantName\": \"Bonab\"}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            loghmeTest.addToCart(testAddToCart);
            String cartJson = loghmeTest.finalizeOrder();

            JsonObject cartObject = gson.fromJson(cartJson, JsonObject.class);

            JsonArray cartArrays = cartObject.get("items").getAsJsonArray();
            JsonObject cartItemObject = cartArrays.get(0).getAsJsonObject();

            JsonElement foodNameElement = cartItemObject.get(Fields.FOOD_NAME);
            String foodName = foodNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : foodNameElement.getAsString();
            Assert.assertEquals(foodName, "Kabab");

            JsonElement restaurantNameElement = cartItemObject.get(Fields.RESTAURANT_NAME);
            String restaurantName = restaurantNameElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantNameElement.getAsString();
            Assert.assertEquals(restaurantName, "Bonab");

            JsonElement countElement = cartItemObject.get("count");
            int count = countElement.getAsInt();
            Assert.assertEquals(1, count);

            String newCartJson = loghmeTest.getCart();
            JsonObject newCartObject = gson.fromJson(newCartJson, JsonObject.class);
            JsonArray newCartArrays = newCartObject.get("items").getAsJsonArray();
            Assert.assertEquals(0, newCartArrays.size());

        } catch(Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetRecommendedRestaurants() {
        String testAddRestaurant1 = "{\"name\": \"one\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant2 = "{\"name\": \"two\", \"description\": \"luxury\", \"location\": {\"x\": 0.5, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant3 = "{\"name\": \"three\", \"description\": \"luxury\", \"location\": {\"x\": 10, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 1000, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant4 = "{\"name\": \"four\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant5 = "{\"name\": \"five\", \"description\": \"luxury\", \"location\": {\"x\": 0.1, \"y\": 0.1}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";

        try {
            loghmeTest.addRestaurant(testAddRestaurant1);
            loghmeTest.addRestaurant(testAddRestaurant2);
            loghmeTest.addRestaurant(testAddRestaurant3);
            loghmeTest.addRestaurant(testAddRestaurant4);
            loghmeTest.addRestaurant(testAddRestaurant5);

            List<String> recommendedRestaurants = loghmeTest.getRecommendedRestaurants();
            Assert.assertTrue(recommendedRestaurants.contains("two"));
            Assert.assertTrue(recommendedRestaurants.contains("three"));
            Assert.assertTrue(recommendedRestaurants.contains("five"));
        } catch(Exception exception) {
            Assert.fail();
        }
    }
}
