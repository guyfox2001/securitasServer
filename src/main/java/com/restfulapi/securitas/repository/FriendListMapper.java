package com.restfulapi.securitas.repository;

import com.restfulapi.securitas.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendListMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultset, int i) throws SQLException{
        return new User((long) -1,
                resultset.getLong("userid"), resultset.getString("name"),
                resultset.getString("surname"), resultset.getDate("birthday"),
                resultset.getString("sex"), null, null);

    }
}
