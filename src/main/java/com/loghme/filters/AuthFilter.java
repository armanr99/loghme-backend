package com.loghme.filters;

import com.google.gson.Gson;
import com.loghme.configs.Configs;
import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.responses.Exception.ExceptionResponse;
import com.loghme.exceptions.UserNotAuthenticated;
import com.loghme.exceptions.UserNotAuthorized;
import com.loghme.models.services.JWTService;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Gson gson = new Gson();

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uri = ((HttpServletRequest) request).getRequestURI();

        if (!uri.equals(Path.Web.LOGIN)
                && !uri.equals(Path.Web.LOGIN + "/google")
                && !uri.equals(Path.Web.SIGNUP)) {
            String header = ((HttpServletRequest) request).getHeader("Authorization");

            if (header != null) {
                authenticateHeader(header, request, response);
            } else {
                setJWTError(response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private void authenticateHeader(String header, ServletRequest request, ServletResponse response)
            throws IOException {
        String token = header.substring(Configs.BEARER_SIZE);
        String subject = JWTService.getInstance().getSubject(token);

        if (subject != null) {
            int userId = Integer.parseInt(subject);
            request.setAttribute("userId", userId);
        } else {
            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(new UserNotAuthenticated(), HttpStatus.FORBIDDEN);
            ((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
            writeErrorResponse(response, exceptionResponse);
        }
    }

    private void setJWTError(ServletResponse response) throws IOException {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new UserNotAuthorized(), HttpStatus.UNAUTHORIZED);
        ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
        writeErrorResponse(response, exceptionResponse);
    }

    private void writeErrorResponse(ServletResponse response, ExceptionResponse exceptionResponse)
            throws IOException {
        byte[] responseToSend = getJsonBytes(exceptionResponse);
        ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
        response.getOutputStream().write(responseToSend);
    }

    private byte[] getJsonBytes(ExceptionResponse exceptionResponse) {
        String objectStr = gson.toJson(exceptionResponse);
        return objectStr.getBytes();
    }
}
