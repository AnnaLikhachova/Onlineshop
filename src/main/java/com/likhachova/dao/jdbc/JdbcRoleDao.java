package com.likhachova.dao.jdbc;

import com.likhachova.dao.RoleDao;
import com.likhachova.dao.UserDao;
import com.likhachova.model.Role;
import com.likhachova.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoleDao implements RoleDao {

    private DataSource dataSource;

    public JdbcRoleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Role getRoleById(int id) {
        String query = "SELECT role FROM roles WHERE id = ?;";
        Role role;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);

        ) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if(!resultSet.next()) {
                    return null;
                }
                String userRole = resultSet.getString("role");
                role = new Role(id, userRole);
                if(resultSet.next()) {
                    throw new IllegalArgumentException("More than one role with such id in database");
                }
                return role;
            }
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot get role from database", e);
        }
    }
}
