package com.loghme.controllers.Login;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.requests.Login.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.Web.LOGIN)
public class LoginController {
    @PostMapping("")
    public String loginUser(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        return "Success";
    }
}
