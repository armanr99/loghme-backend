package com.loghme.controllers.ExceptionHandler;

import com.loghme.controllers.wrappers.Exception.ExceptionWrapper;
import com.loghme.models.Cart.exceptions.CartItemDoesntExist;
import com.loghme.models.Cart.exceptions.DifferentRestaurant;
import com.loghme.models.Cart.exceptions.EmptyCartFinalize;
import com.loghme.models.Food.exceptions.InvalidCount;
import com.loghme.models.Restaurant.exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.exceptions.RestaurantOutOfRange;
import com.loghme.models.User.exceptions.OrderDoesntExist;
import com.loghme.models.Wallet.exceptions.NotEnoughBalance;
import com.loghme.models.Wallet.exceptions.WrongAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    private ResponseEntity<ExceptionWrapper> getExceptionResponse(Exception exception, HttpStatus httpStatus) {
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper(exception, httpStatus);
        return new ResponseEntity<ExceptionWrapper>(exceptionWrapper, httpStatus);
    }

    @ExceptionHandler(RestaurantOutOfRange.class)
    public final ResponseEntity<ExceptionWrapper> handleForbidden(Exception exception, WebRequest request) {
        return getExceptionResponse(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({RestaurantDoesntExist.class,
                       OrderDoesntExist.class})
    public final ResponseEntity<ExceptionWrapper> handleNotFound(Exception exception, WebRequest request) {
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
    public final ResponseEntity<ExceptionWrapper> handleBadRequest(Exception exception, WebRequest request) {
        return getExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

}