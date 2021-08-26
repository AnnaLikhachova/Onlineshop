package com.likhachova.dao;

import com.likhachova.configuration.PropertiesReader;
import com.likhachova.dao.jdbc.JdbcRoleDao;
import com.likhachova.model.Role;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RoleDaoTest {

    @Mock
    private RoleDao roleDao;

    @Before
    public void set() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:~/testdata");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        assertNotNull(dataSource);
        roleDao = new JdbcRoleDao(dataSource);
    }

    @Test
    @DisplayName("Get role by id")
    public void test_DaoReturnAllUsers() {
        Role role = roleDao.getRoleById(1);
        assertNotNull(role);
    }

}
