package com.loghme.controllers.ExceptionHandler;

import com.loghme.controllers.wrappers.responses.Exception.ExceptionResponse;
import com.loghme.models.domain.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.domain.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.domain.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.domain.Food.exceptions.InvalidCount;
import com.loghme.models.domain.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.domain.User.exceptions.OrderDoesntExist;
import com.loghme.models.domain.Wallet.exceptions.NotEnoughBalance;
import com.loghme.models.domain.Wallet.exceptions.WrongAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    private ResponseEntity<ExceptionResponse> getExceptionResponse(Exception exception, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception, httpStatus);
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(RestaurantOutOfRange.class)
    public final ResponseEntity<ExceptionResponse> handleForbidden(Exception exception, WebRequest request) {
        return getExceptionResponse(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({RestaurantDoesntExist.class,
                       OrderDoesntExist.class})
    public final ResponseEntity<ExceptionResponse> handleNotFound(Exception exception, WebRequest request) {
        return getExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WrongAmount.class,
                       NumberFormatException.class,
                       EmptyCartFinalize.class,
                       NotEnoughBalance.class,
                       FoodDoesntExist.class,
                       DifferentRestaurant.class,
                       InvalidCount.class,
                       CartItemDoesntExist.class})
    public final ResponseEntity<ExceptionResponse> handleBadRequest(Exception exception, WebRequest request) {
        return getExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

}