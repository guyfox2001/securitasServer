package com.restfulapi.securitas.repository;

import com.restfulapi.securitas.domain.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MessageMapper implements  RowMapper<Message>{

    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Message(resultSet.getString("from_user"),
                resultSet.getString("to_user"),
                resultSet.getString("text"),
                resultSet.getTimestamp("time"),
                resultSet.getBoolean("read"),
                resultSet.getObject("chat_id", UUID.class));
    }
}
