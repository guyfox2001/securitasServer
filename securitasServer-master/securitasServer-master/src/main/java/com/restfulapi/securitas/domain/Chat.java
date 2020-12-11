package com.restfulapi.securitas.domain;

import java.util.UUID;

public class Chat {
    private String first_user;
    private String sec_user;
    private UUID id;
    private String message;

    public Chat(){

    }
    public Chat(String first_user, String sec_user, UUID id, String message){
        this.first_user = first_user;
        this.sec_user = sec_user;
        this.id = id;
        this.message = message;
    }

    public String get_first() {
        return first_user;
    }

    public void set_first(String first_user) {
        this.first_user = first_user;
    }

    public String get_sec() {
        return sec_user;
    }

    public void set_sec(String sec_user) {
        this.sec_user = sec_user;
    }

    public UUID get_id() {
        return id;
    }

    public void set_id(UUID id) {
        this.id = id;
    }

    public String get_last_m(UUID id) {
        return message;
    }

    public void set_last_m(String message) {
        this.message = message;
    }

}
