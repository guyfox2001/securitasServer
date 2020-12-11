package com.restfulapi.securitas.controller;

import com.restfulapi.securitas.repository.UserRepo;
import com.restfulapi.securitas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService User_Service;
    //private final AuthenticationManager AuthManager;
    //private final PasswordEncoder encoder;
    private final UserRepo UserRepos;

    @Autowired
    public UserController(UserService user_service,
                          /*AuthenticationManager authManager,
                          PasswordEncoder encoder,*/
                          UserRepo userRepos) {
        User_Service = user_service;
        /*AuthManager = authManager;
        this.encoder = encoder;*/
        UserRepos = userRepos;
    }


}
