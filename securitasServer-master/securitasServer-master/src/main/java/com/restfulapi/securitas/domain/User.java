package com.restfulapi.securitas.domain;

import java.util.Date;


public class User {


    private Long user_id;
    private Long NFCID;
    private String name;
    private String surname;
    private Date birthday;
    private String sex;
    private String username;
    private String HASHpassword;


    public User(
            Long   nfcid,
            Long   user_id,
            String name,
            String surname,
            Date   birthday,
            String sex,
            String username,
            String HASHpassword) {
        this.setUser_id(user_id);
        this.setNFCID(nfcid);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setSex(sex);
        this.setUsername(username);
        setHASHpassword(HASHpassword);
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getNFCID() {
        return NFCID;
    }

    public void setNFCID(Long NFCID) {
        this.NFCID = NFCID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHASHpassword() {
        return HASHpassword;
    }

    public void setHASHpassword(String HASHpassword) {
        this.HASHpassword = HASHpassword;
    }
}

