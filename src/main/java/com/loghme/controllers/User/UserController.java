package com.loghme.controllers.User;

import com.loghme.configs.Path;
import com.loghme.configs.UserConfigs;
import com.loghme.controllers.DTOs.responses.User.UserResponse;
import com.loghme.exceptions.*;
import com.loghme.models.domain.User.User;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(Path.Web.USER)
public class UserController {
    @GetMapping("")
    public UserResponse getUser(@RequestAttribute int userId)
            throws UserDoesntExist, FoodDoesntExist, RestaurantDoesntExist, OrderItemDoesntExist,
                    SQLException, OrderDoesntExist {
        User user = UserService.getInstance().getUser(userId);
        return new UserResponse(user);
    }
}
