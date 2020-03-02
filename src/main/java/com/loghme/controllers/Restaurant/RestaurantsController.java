package com.loghme.controllers.Restaurant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loghme.configs.Path;
import com.loghme.configs.Configs;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.repositories.UserRepository;
import com.loghme.repositories.DeliveryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(Path.Web.RESTAURANTS)
public class RestaurantsController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        Location userLocation = UserRepository.getInstance().getUser().getLocation();
        ArrayList<Restaurant> restaurants = RestaurantRepository.getInstance().getRestaurantsWithinDistance(userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
        request.setAttribute("restaurants", restaurants);

        HashMap<String, Long> expectedDeliveries = new HashMap<>();
        DeliveryRepository deliveryRepositoryInstance = DeliveryRepository.getInstance();

        for(Restaurant restaurant : restaurants)
            expectedDeliveries.put(restaurant.getId(), deliveryRepositoryInstance.getExpectedDeliveryTime(restaurant));

        request.setAttribute("expectedDeliveries", expectedDeliveries);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.Jsp.RESTAURANTS);
        requestDispatcher.forward(request, response);
    }
}
