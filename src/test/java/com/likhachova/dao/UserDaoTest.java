package com.likhachova.dao;

import com.likhachova.dao.jdbc.JdbcUserDao;
import com.likhachova.model.User;
import com.likhachova.web.security.PasswordEncoder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    @Mock
    private UserDao userDao;

    @Before
    public void set() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:~/testdata");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        assertNotNull(dataSource);
        userDao = new JdbcUserDao(dataSource);
    }

    @Test
    @DisplayName("Get all users")
    public void test_DaoReturnAllUsers() {
        Iterable<User> users = userDao.getAll();
        assertNotNull(users);
    }

    @Test
    @DisplayName("Get user by name")
    public void test_DaoGetUserByName() {
        User user = new User("user", "user");
        User daoUser = userDao.getUserByName(user.getName());
        assertNotNull(userDao);
        assertEquals(user.getName(), daoUser.getName());
    }
}
