package com.loghme.controllers.User;

import com.loghme.configs.Path;
import com.loghme.controllers.wrappers.User.UserWrapper;
import com.loghme.models.User.User;
import com.loghme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Path.Web.USER)
public class UserController {
    @GetMapping("")
    public UserWrapper getUser() {
        User user = UserRepository.getInstance().getUser();
        return new UserWrapper(user);
    }
}