package com.loghme.controllers.Signup;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.requests.Signup.SignupRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.Web.SIGNUP)
public class SignupController {
    @PostMapping("")
    public String signupUser(@RequestBody SignupRequest request) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String password = request.getPassword();
        String email = request.getEmail();

        return "Success";
    }
}
