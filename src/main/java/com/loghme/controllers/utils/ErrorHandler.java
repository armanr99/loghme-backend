package com.loghme.controllers.utils;

import com.loghme.configs.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler {
    public static void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) throws ServletException, IOException {
        int status = HTTPHandler.getStatusCode(exception);
        response.setStatus(status);
        request.setAttribute("error", exception.toString());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.Jsp.ERROR);
        requestDispatcher.forward(request, response);
    }
}
