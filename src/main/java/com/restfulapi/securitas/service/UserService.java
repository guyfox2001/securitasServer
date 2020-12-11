package com.restfulapi.securitas.service;


import com.restfulapi.securitas.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo User_Repos;

    @Autowired
    public UserService(UserRepo user_repos) {
        User_Repos = user_repos;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.restfulapi.securitas.domain.User _User = User_Repos.getUser(s);
        if (_User == null) throw new UsernameNotFoundException(s + "not found");
        return new User(_User.getUsername(), _User.getHASHpassword(), new ArrayList<>());
    }
}
