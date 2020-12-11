package com.restfulapi.securitas.repository;

import com.restfulapi.securitas.domain.Chat;
import com.restfulapi.securitas.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//securitas_chats
@Repository
public class MessageRepo {

    private final JdbcTemplate JT;

    @Autowired
    public MessageRepo(JdbcTemplate jt) {
        JT = jt;
    }

    public void add_message(Message text){
        JT.update("insert into messages(f_user, t_user, text, time, read, chat_id" +
                        "values(?, ?, ?, ?, ?, ?)",
                text.get_f_user(),
                text.get_t_user(),
                text.get_text(),
                text.get_time(),
                text.read(),
                text.getChat_id()
        );
    }

    public List<Message> get_messages(String f_user, String t_user){
        UUID chat_id = getChat_id(f_user, t_user);

        if (chat_id == null)
            return null;
        return JT.query("select * from messages where dialog_id = & order by time",
                new Object[]{chat_id}, new MessageMapper());


    }

    public UUID getChat_id(String f_user, String s_user){
        return JT.queryForObject("select if from chats where (?, ?) in" +
                        "(user_1, user_2), (user_2, user_1))",
                new Object[]{f_user, s_user}, UUID.class);
    }

    public List<Chat> getChats(String username){
        List<Chat> chats = JT.query("select * from chats where ? in (user_1, user_2)",
                new Object[]{username}, new ChatMapper());//чат маппер это где

        for(Chat chat : chats){
            //chat.set_last_m(get_last_m(chat.get_id()));
            chat.set_last_m(chat.get_last_m(chat.get_id()));
        }
        return chats;
    }

    public void add_chat(Chat chat){
        JT.update("insert into chats (id, user_1, user_2) values (?, ?, ?)",
                chat.get_id(), chat.get_first(), chat.get_sec());
    }

    public void delete_chat(UUID id){
        JT.update("delete from messages where chat_id = ?", id);
        JT.update("delete from chats where id = ?", id);
    }

}
