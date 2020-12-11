package com.restfulapi.securitas.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.restfulapi.securitas.domain.User;
import com.restfulapi.securitas.repository.FriendListRepo;
import com.restfulapi.securitas.repository.MessageRepo;
import com.restfulapi.securitas.repository.UserRepo;
import com.restfulapi.securitas.response.BaseResponse;
import com.restfulapi.securitas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendListController {
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final FriendListRepo friendListRepo;
    private final MessageRepo messageRepo;

    @Autowired
    public FriendListController(JwtUtil jwtUtil, UserRepo userRepo, FriendListRepo friendListRepo, MessageRepo messageRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.friendListRepo = friendListRepo;
        this.messageRepo = messageRepo;
    }

    @PostMapping("/add_friend")
    public BaseResponse add_friend(@RequestHeader("Authorization") String jwt, @RequestParam String friend_username){
        boolean is_friend_exist = userRepo.check_account(friend_username);
        if (is_friend_exist) {
            boolean areFriends = friendListRepo.already_friends(jwtUtil.extractUsername(jwt), friend_username);
            if (areFriends) {
                // уже френдс
                return new BaseResponse("They are friends already", 61);
            } else {
                // все норм
                friendListRepo.already_friends(jwtUtil.extractUsername(jwt), friend_username);
                return new BaseResponse("success", 0);
            }
        } else {
            // username error
            return new BaseResponse("Incorrect friend's username", 62);
        }
    }

    @PostMapping("/friend_info")
    public User friend_info(@RequestHeader("Authorization") String jwt, @RequestParam String friend_username){
        if(!userRepo.check_account(friend_username))
            return null;
        return friendListRepo.get_friend(friend_username);
    }

    @DeleteMapping("/friend")
    public BaseResponse delete_friend(@RequestHeader("Authorization") String jwt, @RequestParam String friend_username){
        friendListRepo.delete_friend(jwtUtil.extractUsername(jwt), friend_username);
        return new BaseResponse("success", 0);
    }

    @DeleteMapping("/chats")
    public BaseResponse delete_chat(@RequestHeader("Authorization") String jwt, @RequestParam String friend_username){
        messageRepo.delete_chat(messageRepo.getChat_id(jwtUtil.extractUsername(jwt), friend_username));
        return new BaseResponse("success", 0);
    }

    @PostMapping("/friends")
    public List<User> get_friend_list(@RequestHeader("Authorization") String jwt, @RequestParam String friend_username){
        return friendListRepo.get_all_friends(jwtUtil.extractUsername(jwt));
    }
}

