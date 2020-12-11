package com.restfulapi.securitas.repository;

import com.restfulapi.securitas.domain.Chat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ChatMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Chat(resultSet.getString("user_1"),
                resultSet.getString("user_2"),
                resultSet.getObject("id", UUID.class), null);
    }
}
