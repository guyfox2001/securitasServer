package com.restfulapi.securitas.repository;


import com.restfulapi.securitas.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

    public final JdbcTemplate JT;
    @Autowired
    public UserRepo(JdbcTemplate _JT) {
        this.JT = _JT;
    }

    public void insertUser(User _User) {
        JT.update("insert into users(nfcid, userid, name, surname," +
                        " birthday, sex, username, hash_password) values (?,?,?,?,?,?,?,?)",
                _User.getNFCID(),
                _User.getUser_id(),
                _User.getName(),
                _User.getSurname(),
                _User.getBirthday(),
                _User.getSex(),
                _User.getUsername(),
                _User.getHASHpassword()
        );
    }

    public User getUser(String user_name) {
        return JT.queryForObject(
                "select * from users where username = ?",
                new Object[]{user_name},
                new UserMapper());
    }

    public User getUser(Long NFC_id) {
        return JT.queryForObject(
                "select * from users where nfcid = ?",
                new Object[]{NFC_id},
                new UserMapper());
    }

    public void updateUser(User _User) {
        JT.update("update users set" +
                        " surname = ?,name = ?, sex = ?, birthday = ? where username = ?",
                _User.getSurname(),
                _User.getName(),
                _User.getSex(),
                _User.getBirthday(),
                _User.getUsername());
    }
    public void deleteUser(String user_name){
        JT.update("delete from users where username = ?",user_name);
    }
}
