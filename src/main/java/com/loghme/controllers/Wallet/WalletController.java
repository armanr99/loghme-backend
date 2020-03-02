package com.loghme.controllers.Wallet;

import com.loghme.configs.Path;
import com.loghme.controllers.utils.ErrorHandler;
import com.loghme.models.Wallet.Exceptions.WrongAmount;
import com.loghme.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(Path.Web.WALLET)
public class WalletController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String amountString = request.getParameter("amount");
            double amount = Double.parseDouble(Objects.requireNonNull(amountString));
            UserRepository.getInstance().chargeUser(amount);
            response.sendRedirect(Path.Web.USER);
        } catch(WrongAmount | NumberFormatException exception) {
            ErrorHandler.handleException(request, response, exception);
        }
    }
}
