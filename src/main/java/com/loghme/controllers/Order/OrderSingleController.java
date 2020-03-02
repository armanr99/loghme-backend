package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.controllers.utils.ErrorHandler;
import com.loghme.controllers.utils.HTTPHandler;
import com.loghme.models.Order.Order;
import com.loghme.models.User.Exceptions.OrderDoesntExist;
import com.loghme.repositories.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Path.Web.ORDER_SINGLE)
public class OrderSingleController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        try {
            String orderId = HTTPHandler.getPathParam(request);
            Order order = UserRepository.getInstance().getOrder(orderId);
            request.setAttribute("order", order);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.Jsp.ORDER_SINGLE);
            requestDispatcher.forward(request, response);
        } catch (OrderDoesntExist orderDoesntExist) {
            ErrorHandler.handleException(request, response, orderDoesntExist);
        }
    }
}
