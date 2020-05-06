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

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();

        if (!uri.equals(Path.Web.LOGIN) && !uri.equals(Path.Web.SIGNUP)) {
            String header = httpServletRequest.getHeader("Authorization");

            if (header != null) {
                String token = header.substring(Configs.BEARER_SIZE);
                String subject = JWTService.getInstance().getSubject(token);

                if (subject != null) {
                    int userId = Integer.parseInt(subject);
                    request.setAttribute("userId", userId);
                } else {
                    ExceptionResponse exceptionResponse =
                            new ExceptionResponse(new UserNotAuthenticated(), HttpStatus.FORBIDDEN);
                    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                    writeErrorResponse(response, exceptionResponse);
                    return;
                }

            } else {
                ExceptionResponse exceptionResponse =
                        new ExceptionResponse(new UserNotAuthorized(), HttpStatus.UNAUTHORIZED);
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                writeErrorResponse(response, exceptionResponse);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private byte[] getJsonBytes(ExceptionResponse exceptionResponse) {
        String objectStr = gson.toJson(exceptionResponse);
        return objectStr.getBytes();
    }

    private void writeErrorResponse(ServletResponse response, ExceptionResponse exceptionResponse)
            throws IOException {
        byte[] responseToSend = getJsonBytes(exceptionResponse);
        ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
        response.getOutputStream().write(responseToSend);
    }
}
