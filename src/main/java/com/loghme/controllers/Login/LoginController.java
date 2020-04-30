package com.loghme.controllers.Login;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.requests.Login.LoginRequest;
import com.loghme.exceptions.WrongLogin;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(Path.Web.LOGIN)
public class LoginController {
    @PostMapping("")
    public String loginUser(@RequestBody LoginRequest request) throws SQLException, WrongLogin {
        String email = request.getEmail();
        String password = request.getPassword();

        UserService.getInstance().loginUser(email, password);
        return "Success";
    }
}
