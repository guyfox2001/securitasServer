package domain;

/*
* Класс дл пользователя
* */


import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private Long user_id;
    private Long NFCID;
    private String name;
    private String surname;
    private String birthday;
    private String sex;
    private String username;
    private String HASHpassword;


    public User(
            Long user_id,
            Long nfcid,
            String name,
            String surname,
            String birthday,
            String sex,
            String username,
            String hasHpassword) {
        this.user_id = user_id;
        this.NFCID = nfcid;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.sex = sex;
        this.username = username;
        HASHpassword = hasHpassword;
    }
}
