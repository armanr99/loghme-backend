package com.loghme.controllers.Signup;

import com.loghme.configs.PathConfigs;
import com.loghme.controllers.DTOs.requests.Signup.SignupRequest;
import com.loghme.controllers.DTOs.responses.Token.TokenResponse;
import com.loghme.exceptions.EmailAlreadyExists;
import com.loghme.models.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(PathConfigs.Web.SIGNUP)
public class SignupController {
    @PostMapping("")
    public TokenResponse signupUser(@RequestBody SignupRequest request)
            throws SQLException, EmailAlreadyExists {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();

        String token =
                UserService.getInstance()
                        .signupUser(firstName, lastName, phoneNumber, email, password);
        return new TokenResponse(token);
    }
}
