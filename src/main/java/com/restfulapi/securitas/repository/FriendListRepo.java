package com.restfulapi.securitas.repository;


import com.restfulapi.securitas.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendListRepo {
    private final JdbcTemplate JT;

    public FriendListRepo(JdbcTemplate jt) {
        JT = jt;
    }

    public boolean already_friends(String user_1, String user_2){
        //чем заменить
        try {
            JT.queryForObject("select sender from friendlist where (user_1 = ? and user_2 = ?" +
                            "or (user_1 = ? and user_2 = ?)",
                    new Object[]{user_1, user_2, user_2, user_1}, String.class);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    public void add_friend(String user_1, String user_2){
        JT.update("insert into friendlist (user_1, user_2) values (?, ?)", user_1, user_2);
    }

    public void delete_friend(String user_1, String user_2){
        JT.update("delete from friendlist where (user_1 = ? and user_2 = ?)" +
                "or (user_1 = ? and user_2 = ?)", user_1, user_2, user_2, user_1);
    }

    public User get_friend(String friend_name){
        return JT.queryForObject("select user_id, name, surname, birthday, sex" +
                        "from users where username = ?",
                new Object[]{friend_name}, new FriendListMapper());
    }

    public List<User> get_all_friends(String friend_name){
        List<String> users_1 = JT.queryForList("select user_1 from friendlist"+
                "where user_2 = ?", new Object[]{friend_name}, String.class);

        List<String> users_2 = JT.queryForList("select user_2 from friendlist"+
                "where user_1 = ?", new Object[]{friend_name}, String.class);

        users_1.addAll(users_2);

        ArrayList<User> friends = new ArrayList<>();
        for(String user1 : users_1) {
            friends.add(JT.queryForObject("select * from users where username = ?",
                    new Object[]{user1}, new FriendListMapper()));
        }
        return  friends;
    }
}
