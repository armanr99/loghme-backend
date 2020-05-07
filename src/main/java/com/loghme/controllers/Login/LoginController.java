package com.loghme.controllers.Login;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.requests.Login.LoginGoogleRequest;
import com.loghme.controllers.DTOs.requests.Login.LoginRequest;
import com.loghme.controllers.DTOs.responses.Token.TokenResponse;
import com.loghme.exceptions.WrongLogin;
import com.loghme.models.services.JWTService;
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
    public TokenResponse loginUser(@RequestBody LoginRequest request)
            throws SQLException, WrongLogin {
        String email = request.getEmail();
        String password = request.getPassword();
        String token = UserService.getInstance().loginUser(email, password);

        return new TokenResponse(token);
    }

    @PostMapping("/google")
    public TokenResponse loginGoogleUser(@RequestBody LoginGoogleRequest request)
            throws SQLException, WrongLogin {
        String googleToken = request.getToken();
        String token = UserService.getInstance().loginGoogleUser(googleToken);

        return new TokenResponse(token);
    }
}
