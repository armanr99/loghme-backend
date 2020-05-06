package com.loghme.controllers.ExceptionHandler;

import com.loghme.controllers.DTOs.responses.Exception.ExceptionResponse;
import com.loghme.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<ExceptionResponse> getExceptionResponse(
            Exception exception, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception, httpStatus);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler({RestaurantOutOfRange.class, WrongLogin.class, ForbiddenAccess.class})
    public final ResponseEntity<ExceptionResponse> handleForbidden(Exception exception) {
        return getExceptionResponse(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({RestaurantDoesntExist.class, OrderDoesntExist.class, UserDoesntExist.class})
    public final ResponseEntity<ExceptionResponse> handleNotFound(Exception exception) {
        return getExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        WrongAmount.class,
        NumberFormatException.class,
        EmptyCart.class,
        NotEnoughBalance.class,
        FoodDoesntExist.class,
        DifferentRestaurant.class,
        InvalidCount.class,
        CartItemDoesntExist.class,
        EmailAlreadyExists.class
    })
    public final ResponseEntity<ExceptionResponse> handleBadRequest(Exception exception) {
        return getExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }
}
