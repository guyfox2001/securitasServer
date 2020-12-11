package com.restfulapi.securitas.request;

public class AuthRequest {
    public String password;
    public String user_name;

    public AuthRequest(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
