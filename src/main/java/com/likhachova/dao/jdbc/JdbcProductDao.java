package com.likhachova.dao.jdbc;

import com.likhachova.dao.ProductDao;
import com.likhachova.dao.jdbc.mapper.ProductRowMapper;
import com.likhachova.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcProductDao.class);

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM products;";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name = ?, price = ?, description= ?, date = ? WHERE id = ?;";
    private static final String INSERT_PRODUCT = "INSERT INTO products (name, price, description, date) Values (?, ?, ?, ?);";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?;";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, description, price, date FROM products WHERE id = ?;";
    private static final String SELECT_PRODUCT_BY_NAME = "SELECT id, name, price, description, date FROM products WHERE name = ?;";
    private static final String SELECT_LIKE = "SELECT * FROM products WHERE description LIKE ? OR name LIKE ?;";

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(SELECT_ALL, new ProductRowMapper());
    }

    @Override
    public void updateProduct(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT, new ProductRowMapper(), product.getId());
    }

    @Override
    public void addProduct(Product product) {
        jdbcTemplate.update(INSERT_PRODUCT, product.getName(), product.getPrice(), product.getDescription(), product.getDate());
    }

    @Override
    public void deleteProduct(int id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }

    @Override
    public Product getProductById(int id) {
        Product product =  jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID, new Object[] { id },
                new ProductRowMapper());;
        return product;
    }

    @Override
    public Product getProductByName(String name) {
        Product product =  jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_NAME, new Object[] { name },
                new ProductRowMapper());
        return product;
    }

    @Override
    public List<Product> getProductsByDescription(String description) {
        return jdbcTemplate.query(SELECT_LIKE, new Object[] { "%" + description + "%", "%" + description + "%" },
                new ProductRowMapper());
    }
}
