package com.loghme.controllers.User;

import com.loghme.configs.Path;
import com.loghme.models.User.User;
import com.loghme.repositories.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Path.Web.USER)
public class UserController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        User user = UserRepository.getInstance().getUser();
        request.setAttribute("user", user);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.Jsp.USER);
        requestDispatcher.forward(request, response);
    }
}