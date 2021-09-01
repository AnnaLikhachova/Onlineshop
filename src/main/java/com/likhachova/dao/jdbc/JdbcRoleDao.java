package com.likhachova.dao.jdbc;

import com.likhachova.dao.RoleDao;
import com.likhachova.dao.jdbc.mapper.RoleRowMapper;
import com.likhachova.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcRoleDao implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRoleDao.class);

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ROLE_BY_ID = "SELECT role FROM roles WHERE id = ?;";


    public JdbcRoleDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Role getRoleById(int id) {
        Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_ID, new Object[] { id },
                new RoleRowMapper());
        return role;
    }
}
