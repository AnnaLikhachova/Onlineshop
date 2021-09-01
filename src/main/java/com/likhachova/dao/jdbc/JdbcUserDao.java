package com.likhachova.dao.jdbc;

import com.likhachova.dao.UserDao;
import com.likhachova.dao.jdbc.mapper.UserRowMapper;
import com.likhachova.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM users;";
    private static final String SELECT_USER_BY_NAME = "SELECT id, login, password FROM users WHERE login = ?;";

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new UserRowMapper());
    }

    @Override
    public User getUserByName(String login) {
        User user = jdbcTemplate.queryForObject(SELECT_USER_BY_NAME, new Object[] { login },
                new UserRowMapper());
        return user;
    }
}
