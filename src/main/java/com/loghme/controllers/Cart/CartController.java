package com.loghme.controllers.Cart;

import com.loghme.configs.Path;
import com.loghme.controllers.utils.ErrorHandler;
import com.loghme.models.Cart.Cart;
import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Food.Exceptions.InvalidCount;
import com.loghme.models.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.repositories.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Path.Web.CART)
public class CartController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String responseForwardPath = Path.Jsp.NON_EMPTY_CART;

        Cart cart = UserRepository.getInstance().getUser().getCart();

        if(cart.getCartItemsList().size() == 0)
            responseForwardPath = Path.Jsp.EMPTY_CART;
        else
            request.setAttribute("cart", cart);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(responseForwardPath);
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String foodName = request.getParameter("foodName");
            String restaurantId = request.getParameter("restaurantId");
            UserRepository.getInstance().addToCart(foodName, restaurantId);
            response.sendRedirect(Path.Web.RESTAURANTS + "/" + restaurantId);
        } catch (RestaurantDoesntExist | FoodDoesntExist | DifferentRestaurant | RestaurantOutOfRange | InvalidCount exception) {
            ErrorHandler.handleException(request, response, exception);
        }
    }
}
