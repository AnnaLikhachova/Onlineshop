package com.likhachova.dao.jdbc;

import com.likhachova.dao.UserDao;
import com.likhachova.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String SELECT_ALL = "SELECT * FROM users;";
    private static final String SELECT_USER_BY_NAME = "SELECT id, login, password FROM users WHERE login = ?;";

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                users.add(new User(userId, login, password));
            }
            return users;
        }
        catch(SQLException e) {
            logger.error("Cannot get all users from database");
            throw new RuntimeException("Cannot get all users from database", e);
        }
    }

    @Override
    public User getUserByName(String login) {
        User user;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME);

        ) {
            preparedStatement.setString(1, login);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if(!resultSet.next()) {
                    logger.error("No user with such credentials in database");
                    return null;
                }
                int userId = resultSet.getInt("id");
                String userLogin = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(userId, userLogin, password);
                if(resultSet.next()) {
                    logger.error("More than one user with such login in database");
                    throw new IllegalArgumentException("More than one user with such login in database");
                }
                return user;
            }
        }
        catch(SQLException e) {
            logger.error("Cannot get user from database");
            throw new RuntimeException("Cannot get user from database", e);
        }
    }
}
