package com.loghme.controllers.DTOs.requests.Signup;

public class SignupRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
