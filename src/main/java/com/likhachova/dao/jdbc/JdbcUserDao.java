package com.likhachova.dao.jdbc;

import com.likhachova.dao.UserDao;
import com.likhachova.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users;";
        List<User> users = new ArrayList<>();
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
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
            throw new RuntimeException("Cannot get all users from database", e);
        }
    }

    @Override
    public User getUserByName(String login) {
        String query = "SELECT id, login, password FROM users WHERE login = ?;";
        User user;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);

        ) {
            preparedStatement.setString(1, login);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if(!resultSet.next()) {
                    return null;
                }
                int userId = resultSet.getInt("id");
                String userLogin = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(userId, userLogin, password);
                if(resultSet.next()) {
                    throw new IllegalArgumentException("More than one user with such login in database");
                }
                return user;
            }
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot get user from database", e);
        }
    }
}
