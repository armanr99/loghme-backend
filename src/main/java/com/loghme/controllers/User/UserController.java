package com.loghme.controllers.User;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.responses.User.UserResponse;
import com.loghme.models.domain.User.User;
import com.loghme.models.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Path.Web.USER)
public class UserController {
    @GetMapping("")
    public UserResponse getUser() {
        User user = UserRepository.getInstance().getUser();
        return new UserResponse(user);
    }
}