package com.loghme.models.domain.Loghme;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.configs.Fields;
import com.loghme.configs.GeneralConstants;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Food.Food;
import com.loghme.models.domain.Restaurant.*;
import com.loghme.models.domain.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantAlreadyExists;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.domain.User.User;
import com.loghme.models.repositories.RestaurantRepository;
import com.loghme.models.repositories.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

public class LoghmeTest {
    private Loghme loghmeTest;

    @Before
    public void setup() {
        loghmeTest = new Loghme();
    }

    @After
    public void tearDown() {
        loghmeTest = null;
        UserRepository.clearInstance();
        RestaurantRepository.clearInstance();
    }

    @Test
    public void testAddRestaurant() {
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);

            Restaurant hesturan = RestaurantRepository.getInstance().getRestaurantInstance("1");
            Assert.assertEquals(hesturan.getName(), "Hesturan");
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
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddFoodJson = "{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"restaurantId\": \"1\", \"price\": 20000}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            loghmeTest.addFood(testAddFoodJson);

            Restaurant hesturan = RestaurantRepository.getInstance().getRestaurantInstance("1");
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
        String testAddRestaurantJson1 = "{\"id\": \"1\", \"name\": \"Bonab\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddRestaurantJson2 = "{\"id\": \"2\", \"name\": \"Hani\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson1);
            loghmeTest.addRestaurant(testAddRestaurantJson2);

            List<String> restaurants = loghmeTest.getRestaurants();
            Assert.assertTrue(restaurants.contains("1"));
            Assert.assertTrue(restaurants.contains("2"));
        } catch(Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetRestaurant() {
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testGetRestaurantJson = "{\"id\": \"1\"}";

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
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testGetFoodsJson = "{\"foodName\": \"Kabab\", \"restaurantId\": \"1\"}";

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
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantId\": \"1\"}";

        try {
            loghmeTest.addRestaurant(testAddRestaurantJson);
            loghmeTest.addToCart(testAddToCart);

            User user = UserRepository.getInstance().getUser();

            List<CartItem> userCartItems = user.getCartItemsList();
            Assert.assertEquals(1, userCartItems.size());
            Assert.assertEquals("Kabab", userCartItems.get(0).getFoodName());
            Assert.assertEquals("1", userCartItems.get(0).getRestaurantId());

        } catch(Exception exception) {
            Assert.fail();
        }
    }

   @Test(expected = DifferentRestaurant.class)
   public void testAddToCartDifferentRestaurant() throws DifferentRestaurant {
       String testAddRestaurantJson1 = "{\"id\": \"1\", \"name\": \"Bonab\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
               "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
               "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
               "30000}]}";
       String testAddRestaurantJson2 = "{\"id\": \"2\", \"name\": \"Hani\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
               "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
               "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
               "30000}]}";
       String testAddToCart1 = "{\"foodName\": \"Kabab\", \"restaurantId\": \"1\"}";
       String testAddToCart2 = "{\"foodName\": \"Kabab\", \"restaurantId\": \"2\"}";

       try {
           loghmeTest.addRestaurant(testAddRestaurantJson1);
           loghmeTest.addRestaurant(testAddRestaurantJson2);
           loghmeTest.addToCart(testAddToCart1);
           loghmeTest.addToCart(testAddToCart2);
       } catch(RestaurantAlreadyExists | RestaurantDoesntExist | FoodDoesntExist | RestaurantOutOfRange | InvalidCount exception) {
           Assert.fail();
       }
   }

   @Test
   public void testGetCart() {
        Gson gson = new Gson();
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Bonab\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
               "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
               "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
               "30000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantId\": \"1\"}";

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

            JsonElement restaurantIdElement = cartItemObject.get(Fields.RESTAURANT_Id);
            String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();
            Assert.assertEquals(restaurantId, "1");

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
        String testAddRestaurantJson = "{\"id\": \"1\", \"name\": \"Bonab\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}," +
                "\"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                "20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\":" +
                "30000}]}";
        String testAddToCart = "{\"foodName\": \"Kabab\", \"restaurantId\": \"1\"}";

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

            JsonElement restaurantIdElement = cartItemObject.get(Fields.RESTAURANT_Id);
            String restaurantId = restaurantIdElement.isJsonNull() ? GeneralConstants.EMPTY_STRING : restaurantIdElement.getAsString();
            Assert.assertEquals(restaurantId, "1");

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
        String testAddRestaurant1 = "{\"id\": \"1\", \"name\": \"one\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant2 = "{\"id\": \"2\", \"name\": \"two\", \"logo\": \"luxury\", \"location\": {\"x\": 0.5, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant3 = "{\"id\": \"3\", \"name\": \"three\", \"logo\": \"luxury\", \"location\": {\"x\": 10, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 1000, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant4 = "{\"id\": \"4\", \"name\": \"four\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";
        String testAddRestaurant5 = "{\"id\": \"5\", \"name\": \"five\", \"logo\": \"luxury\", \"location\": {\"x\": 0.1, \"y\": 0.1}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\": 20000}, {\"name\": \"Kabab\", \"description\": \"it's delicious!\", \"popularity\": 0.6, \"price\": 30000}]}";

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
