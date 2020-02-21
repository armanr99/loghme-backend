package com.loghme.controllers.Restaurant;

import com.loghme.configs.Configs;
import com.loghme.configs.Path;
import com.loghme.controllers.utils.ErrorHandler;
import com.loghme.controllers.utils.HTTPHandler;
import com.loghme.models.Location.Location;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;
import com.loghme.repositories.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Path.Web.RESTAURANT)
public class RestaurantController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        try {
            Location userLocation = UserRepository.getInstance().getUser().getLocation();
            RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
            String restaurantId = HTTPHandler.getPathParam(request);
            Restaurant restaurant = restaurantRepository.getRestaurantInstanceIfInRange(restaurantId, userLocation, Configs.VISIBLE_RESTAURANTS_DISTANCE);
            request.setAttribute("restaurant", restaurant);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.jsp.RESTAURANT);
            requestDispatcher.forward(request, response);
        } catch (RestaurantDoesntExist | RestaurantOutOfRange exception) {
            ErrorHandler.handleException(request, response, exception);
        }
    }
}