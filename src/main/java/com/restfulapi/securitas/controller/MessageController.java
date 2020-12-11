package com.restfulapi.securitas.controller;

import com.restfulapi.securitas.domain.Chat;
import com.restfulapi.securitas.domain.Message;
import com.restfulapi.securitas.repository.FriendListRepo;
import com.restfulapi.securitas.repository.MessageRepo;
import com.restfulapi.securitas.response.BaseResponse;
import com.restfulapi.securitas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class MessageController {
    private final MessageRepo messageRepo;
    private final FriendListRepo friendListRepo;
    private final JwtUtil jwtUtil;

    @Autowired
    public MessageController(MessageRepo messageRepo, FriendListRepo friendListRepo, JwtUtil jwtUtil) {
        this.messageRepo = messageRepo;
        this.friendListRepo = friendListRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/message")
    public BaseResponse send_message(@RequestBody Message text, @RequestHeader("Authorization") String jwt){
        text.setChat_id(UUID.randomUUID());
        text.set_read(false);
        UUID chat_id = messageRepo.getChat_id(text.get_f_user(), text.get_t_user());
        if(chat_id == null){
            Chat chat = new Chat(text.get_f_user(), text.get_t_user(), UUID.randomUUID(), null);
            messageRepo.add_chat(chat);
            chat_id = chat.get_id();
        }
        text.setChat_id(chat_id);
        messageRepo.add_message(text);
        return new BaseResponse("success", 0);
    }

    @PostMapping("/messages")
    public List<Message> get_messages(@RequestParam String friend_username, @RequestHeader("Authorization") String jwt){
        String username = jwtUtil.extractUsername(jwt);
        return messageRepo.get_messages(username, friend_username);
    }

    @PostMapping("/chats")
    public List<Chat> get_chats(@RequestHeader("Authorization") String jwt){
        String username = jwtUtil.extractUsername(jwt);
        return messageRepo.getChats(username);
    }
}
