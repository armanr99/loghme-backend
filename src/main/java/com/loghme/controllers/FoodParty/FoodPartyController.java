package com.loghme.controllers.FoodParty;

import com.loghme.configs.Path;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(Path.Web.FOOD_PARTY)
public class FoodPartyController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        ArrayList<Restaurant> foodPartyRestaurants = RestaurantRepository.getInstance().getFoodPartyRestaurants();
        request.setAttribute("restaurants", foodPartyRestaurants);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.Jsp.FOOD_PARTY);
        requestDispatcher.forward(request, response);
    }
}
