package com.loghme.controllers.utils;

import com.loghme.models.Cart.Exceptions.DifferentRestaurant;
import com.loghme.models.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.models.Restaurant.Exceptions.FoodDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantDoesntExist;
import com.loghme.models.Restaurant.Exceptions.RestaurantOutOfRange;
import com.loghme.models.User.Exceptions.OrderDoesntExist;
import com.loghme.models.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.models.Wallet.Exceptions.WrongAmount;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

import static com.loghme.configs.GeneralConstants.PATH_DELIM;

public class HTTPHandler {
    public static String getPathParam(HttpServletRequest request) {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), PATH_DELIM);
        return tokenizer.nextToken();
    }

    static int getStatusCode(Exception exception) {
        int statusCode = 500;

        if(exception instanceof RestaurantOutOfRange)
            statusCode = 403;
        else if(exception instanceof RestaurantDoesntExist ||
                exception instanceof OrderDoesntExist)
            statusCode = 404;
        else if(exception instanceof WrongAmount ||
                exception instanceof NumberFormatException ||
                exception instanceof EmptyCartFinalize ||
                exception instanceof NotEnoughBalance ||
                exception instanceof FoodDoesntExist ||
                exception instanceof DifferentRestaurant)
            statusCode = 400;

        return statusCode;
    }
}
