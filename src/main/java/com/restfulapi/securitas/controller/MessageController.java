package com.restfulapi.securitas.controller;

import com.restfulapi.securitas.domain.Message;
import com.restfulapi.securitas.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }



    @PostMapping("/message")
    public void/*BaseResponse*/ send_message(@RequestBody Message text, @RequestHeader("Authorization") String jwt){
        //if пользователь не найден(){
        //return new BaseResponse(ошибка пользователь не найден);}
        //тут еще if
        //
        text.setChat_id(UUID.randomUUID());
        text.set_read(false);
        //
        UUID chat_id = messageRepo.getChat_id(text.get_f_user(), text.get_t_user());
        text.setChat_id(chat_id);
        messageRepo.add_message(text);
        //return new BaseResponse(успех);
    }
}
