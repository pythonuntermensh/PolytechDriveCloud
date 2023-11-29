package com.polytech.drive.Controller;

import com.polytech.drive.DTO.LoginDTO;
import com.polytech.drive.DTO.RegistrationDTO;
import com.polytech.drive.Entity.UserEntity;
import com.polytech.drive.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getEmail(), body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }
}
