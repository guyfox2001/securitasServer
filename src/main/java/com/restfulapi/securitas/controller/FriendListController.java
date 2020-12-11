package com.restfulapi.securitas.controller;


import com.restfulapi.securitas.repository.FriendListRepo;
import com.restfulapi.securitas.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendListController {
    //создаём из секур токенс
    private final FriendListRepo friendListRepo;
    private final MessageRepo messageRepo;
    @Autowired
    public FriendListController(FriendListRepo friendListRepo, MessageRepo messageRepo) {
        this.friendListRepo = friendListRepo;
        this.messageRepo = messageRepo;
    }

    //@PostMapping("/addFriend")
    //public BaseResponse
    //@DeleteMapping("/friend") delete
    //
    //@PostMapping("/friend") show
    //show all
}

