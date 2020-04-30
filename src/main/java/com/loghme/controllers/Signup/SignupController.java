package com.loghme.controllers.Signup;

import com.loghme.configs.Path;
import com.loghme.controllers.DTOs.requests.Signup.SignupRequest;
import com.loghme.models.services.UserService;
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
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();

        UserService.getInstance().signupUser(firstName, lastName, phoneNumber, email, password);
        return "Success";
    }
}
