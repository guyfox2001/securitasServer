package com.restfulapi.securitas.repository;
import com.restfulapi.securitas.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(
                resultSet.getLong("nfcid"),
                resultSet.getLong("userid"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getDate("birthday"),
                resultSet.getString("sex"),
                resultSet.getString("username"),
                resultSet.getString("HASHpassword")
                );
    }
}
