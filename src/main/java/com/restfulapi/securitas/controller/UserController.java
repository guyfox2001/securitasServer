package com.restfulapi.securitas.controller;

import com.restfulapi.securitas.domain.User;
import com.restfulapi.securitas.repository.UserRepo;
import com.restfulapi.securitas.response.AuthResponse;
import com.restfulapi.securitas.response.BaseResponse;
import com.restfulapi.securitas.security.JwtUtil;
import com.restfulapi.securitas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService User_Service;
    private final AuthenticationManager AuthManager;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final UserRepo UserRepos;

    @Autowired
    public UserController(UserService user_service,
                          AuthenticationManager authManager,
                          PasswordEncoder encoder,
                          JwtUtil jwtUtil, UserRepo userRepos) {
        User_Service = user_service;
        AuthManager = authManager;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        UserRepos = userRepos;
    }

    @PostMapping("/account")
    public BaseResponse create_user(@RequestBody User addingUser){
        if(addingUser.getUsername() == null ||
            addingUser.getName() == null ||
                addingUser.getHASHpassword() == null
        )
            return new BaseResponse("Failed: Bad DataRequest", 3);
        if (UserRepos.check_account(addingUser.getUsername()))
            return new BaseResponse("Failed: That User_Name already used", 1);
        if (UserRepos.check_account(addingUser.getNFCID()))
            return new BaseResponse("Failed: That NFCid ualready used", 2);
        addingUser.setHASHpassword(encoder.encode(addingUser.getHASHpassword()));
        UserRepos.insertUser(addingUser);
        return new BaseResponse("Succes!", 0);
    }
    @PostMapping("/login")
    public AuthResponse login_user(@RequestBody User loggingUser){
        try {
            AuthManager.authenticate(new UsernamePasswordAuthenticationToken(loggingUser.getUsername(), loggingUser.getHASHpassword()));
        }
        catch (BadCredentialsException e){
            return  new AuthResponse("Failed: Username or password wrong", 4, "");
        }
        final UserDetails userDetails = User_Service.loadUserByUsername(loggingUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthResponse("Succes!", 0 , jwt);
    }
//    @PostMapping("/loginNFC")
//    public AuthResponse login_user(@RequestHeader("Auth") Long NFCid){
//
//
//    }
    @DeleteMapping("/account")
    public BaseResponse delete_user(@RequestHeader("Auth") String username){
        UserRepos.deleteUser(username);
        return new BaseResponse("Succes!", 0);
    }
}
