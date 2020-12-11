package com.restfulapi.securitas.domain;

import java.sql.Timestamp;
import java.util.UUID;

public class Message {
    private String f_user;
    private String t_user;
    private String text;
    private Timestamp time;
    private boolean read;
    private UUID chat_id;
    public Message(String from_user, String to_user, String text, Timestamp time, boolean read, UUID chat_id){
        this.f_user = from_user;
        this.t_user = to_user;
        this.text = text;
        this.time = time;
        this.read = read;
        this.chat_id = chat_id;
    }
    public Message(){}
//int na UUID
    public String get_f_user(){return f_user;}

    public void set_f_user(String user){this.f_user = user;}

    public String get_t_user(){return t_user;}

    public void set_t_user(String user){this.t_user = user;}

    public String get_text(){return text;}

    public void set_text(String text){this.text = text;}

    public Timestamp get_time() {
        return time;
    }

    public void set_time(Timestamp time){this.time = time;}

    public boolean read(){return read;}

    public void set_read(boolean read){this.read = read;}

    public UUID getChat_id(){return chat_id;}

    public void setChat_id(UUID id){this.chat_id = id;}
}
