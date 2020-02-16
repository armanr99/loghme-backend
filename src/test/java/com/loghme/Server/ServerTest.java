package com.loghme.Server;

import com.loghme.Constants.Path;
import com.loghme.Constants.ServerConfigs;
import com.loghme.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.Restaurant.RestaurantRepository;
import com.loghme.User.UserRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class ServerTest {
    private static Server server = new Server();

    @BeforeClass
    public static void setupClass() {
        server.start();
    }

    @After
    public void tearDown() {
        UserRepository.clearInstance();
    }


    @Test
    public void GET_to_fetch_restaurant200() {
        try {
            String testGetRestaurantJson = "{\"id\": \"1\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": -21.0, \"y\": -21.0}," +
                    "\"menu\": [{\"name\": \"Gheime\", \"image\": \"test\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                    "20000}]}";

            try {
                RestaurantRepository.getInstance().addRestaurant(testGetRestaurantJson);
            } catch (RestaurantAlreadyExists e) {
                Assert.fail();
            }

            String expectedGetRestaurantBody = "<html>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<head>\n" +
                    "    <title>Loghme</title>\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<main>\n" +
                    "    <div id=\"content\">\n" +
                    "         <div class=\"topnav\">\n" +
                    "          <a class=\"active\" href=\"/restaurants\">Restaurants</a>\n" +
                    "          <a href=\"/user\">Profile</a>\n" +
                    "          <a href=\"/cart\">Cart</a>\n" +
                    "        </div>\n" +
                    "                <h1>Hesturan</h1>\n" +
                    "    <h3>Location: -21.0, -21.0</h3>\n" +
                    "    <div class=\"restaurant\">\n" +
                    "        <div class=\"restaurantLogo\">\n" +
                    "            <img src=\"luxury\" alt=\"Hesturan\">\n" +
                    "        </div>\n" +
                    "        <div class=\"row row-3\">\n" +
                    "                            <div class=\"col\">\n" +
                    "                    <div class=\"food\">\n" +
                    "                        <strong>Name: Gheime</strong><br>\n" +
                    "                        <strong>Description it's yummy!</strong><br>\n" +
                    "                        <strong>Price: 20000.0</strong><br>\n" +
                    "                        <strong>Popularity: 0.8</strong><br>\n" +
                    "                        <form action=\"/food\" method=\"POST\">\n" +
                    "                            <input type=\"hidden\" name=\"foodName\" value=\"Gheime\" />\n" +
                    "                            <input type=\"hidden\" name=\"restaurantId\" value=1 />\n" +
                    "                            <button type=\"submit\">addToCart</button>\n" +
                    "                        </form>\n" +
                    "                    </div><br>\n" +
                    "                </div>\n" +
                    "                    </div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    </div>\n" +
                    "</main>\n" +
                    "</body>\n" +
                    "</html>";

            HttpResponse response = Unirest.get("http://localhost:" + ServerConfigs.PORT + Path.Web.RESTAURANTS + "/1").asString();
            Assert.assertEquals(200, response.getStatus());

            Tidy tidy = new Tidy();
            tidy.setXHTML(true);
            tidy.setIndentContent(true);

            Document expectedGetRestaurantBodyDoc = tidy.parseDOM(new ByteArrayInputStream(expectedGetRestaurantBody.getBytes()), null);
            OutputStream expectedOut = new ByteArrayOutputStream();
            tidy.pprint(expectedGetRestaurantBodyDoc, expectedOut);
            expectedGetRestaurantBody = expectedOut.toString();

            Document actualGetRestaurantBodyDoc = tidy.parseDOM(new ByteArrayInputStream(response.getBody().toString().getBytes()), null);
            OutputStream actualOut = new ByteArrayOutputStream();
            tidy.pprint(actualGetRestaurantBodyDoc, actualOut);

            Assert.assertEquals(expectedGetRestaurantBody, actualOut.toString());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void GET_to_fetch_restaurant404() {
        try {
            HttpResponse response = Unirest.get("http://localhost:" + ServerConfigs.PORT + Path.Web.RESTAURANTS + "/this-id-does-not-exist").asString();
            Assert.assertEquals(404, response.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void Get_to_fetch_restaurant403() {
        try {
            String testGetRestaurantJson = "{\"id\": \"403-access-denied\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": -180.0, \"y\": -180.0}," +
                    "\"menu\": [{\"name\": \"Gheime\", \"image\": \"test\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                    "20000}]}";

            try {
                RestaurantRepository.getInstance().addRestaurant(testGetRestaurantJson);
            } catch (RestaurantAlreadyExists e) {
                Assert.fail();
            }

            HttpResponse response = Unirest.get("http://localhost:" + ServerConfigs.PORT + Path.Web.RESTAURANTS + "/403-access-denied").asString();
            Assert.assertEquals(403, response.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void Get_to_fetch_restaurant200Tight() {
        try {
            String testGetRestaurantJson = "{\"id\": \"200-tight\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 120.20815280171307914814354155782, \"y\": 120.20815280171307914814354155782}," +
                    "\"menu\": [{\"name\": \"Gheime\", \"image\": \"test\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                    "20000}]}";

            try {
                RestaurantRepository.getInstance().addRestaurant(testGetRestaurantJson);
            } catch (RestaurantAlreadyExists e) {
                Assert.fail();
            }

            HttpResponse response = Unirest.get("http://localhost:" + ServerConfigs.PORT + Path.Web.RESTAURANTS + "/200-tight").asString();
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void POST_to_finalize_cart400_empty_cart() {
        try {
            HttpResponse response = Unirest.post("http://localhost:" + ServerConfigs.PORT + Path.Web.FINALIZE_CART).asString();
            Assert.assertEquals(400, response.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void POST_to_finalize_cart400_not_enough_money() {
        try {
            String testGetRestaurantJson = "{\"id\": \"expensive-restaurant\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 1, \"y\": 1}," +
                    "\"menu\": [{\"name\": \"Gheime\", \"image\": \"test\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                    "20000000000}]}";
            RestaurantRepository.getInstance().addRestaurant(testGetRestaurantJson);
            Unirest.post("http://localhost:" + ServerConfigs.PORT + Path.Web.FOOD).field("foodName", "Gheime").field("restaurantId", "expensive-restaurant").asString();
            HttpResponse response = Unirest.post("http://localhost:" + ServerConfigs.PORT + Path.Web.FINALIZE_CART).asString();
            Assert.assertEquals(400, response.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void POST_to_finalize_cart302_success() {
        try {
            String testGetRestaurantJson = "{\"id\": \"cheap-restaurant\", \"name\": \"Hesturan\", \"logo\": \"luxury\", \"location\": {\"x\": 10, \"y\": 10}," +
                    "\"menu\": [{\"name\": \"Gheime\", \"image\": \"test\", \"description\": \"it's yummy!\", \"popularity\": 0.8, \"price\":" +
                    "1}]}";
            RestaurantRepository.getInstance().addRestaurant(testGetRestaurantJson);
            Unirest.post("http://localhost:" + ServerConfigs.PORT + Path.Web.FOOD).field("foodName", "Gheime").field("restaurantId", "cheap-restaurant").asString();
            HttpResponse response = Unirest.post("http://localhost:" + ServerConfigs.PORT + Path.Web.FINALIZE_CART).asString();
            Assert.assertEquals(302, response.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
