package com.loghme.controllers.Order;

import com.loghme.configs.Path;
import com.loghme.controllers.utils.ErrorHandler;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Path.Web.ORDER)
public class OrderController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserRepository.getInstance().finalizeOrder();
            response.sendRedirect(Path.Web.USER);
        } catch (EmptyCartFinalize | NotEnoughBalance exception) {
            ErrorHandler.handleException(request, response, exception);
        }
    }
}
