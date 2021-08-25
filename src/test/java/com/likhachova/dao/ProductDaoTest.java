package com.likhachova.dao;

import com.likhachova.configuration.PropertiesReader;
import com.likhachova.dao.jdbc.JdbcProductDao;
import com.likhachova.model.Product;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest {

    private ProductDao productDao;
    private Connection connection;

    @BeforeEach
    public void set() {
        String pathToProperties = "application.properties";
        PropertiesReader propertiesReader = new PropertiesReader(pathToProperties);
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("products");
        dataSource.setPassword("password");
        dataSource.setServerName("jdbc:mysql://localhost:3306/products?");
        productDao = new JdbcProductDao(dataSource);
    }

    @Test
    public void testA_DaoReturnListOfProducts() {
        Iterable<Product> products = productDao.getAllProducts();
        assertNotNull(products);
    }

    @Test
    public void testB_DaoAddProduct() {
        Product product = new Product("Apple", 60, "Green Apple from Canada", LocalDate.of(2021, 8, 8));
        productDao.addProduct(product);
        Product productD = productDao.getProductByName("Apple");
        assertNotNull(product);
        assertEquals(product.getName(), productD.getName());
        assertEquals(product.getPrice(), productD.getPrice());
        assertEquals(product.getDescription(), productD.getDescription());
        assertEquals(product.getDate(), productD.getDate());
    }

    @Test
    public void testC_DaoUpdateProduct() {
        Product product = productDao.getProductByName("Apple");
        assertNotNull(product);
        product.setName("Carrot");
        product.setPrice(90);
        product.setDate(LocalDate.of(2021, 8, 7));
        productDao.updateProduct(product);
        Product productD = productDao.getProductByName("Carrot");
        assertNotNull(product);
        assertEquals(product.getName(), productD.getName());
        assertEquals(product.getPrice(), productD.getPrice());
        assertEquals(product.getDescription(), productD.getDescription());
        assertEquals(product.getDate(), productD.getDate());
    }

    @Test
    public void testD_DaoDeleteProduct() {
        Product product = productDao.getProductByName("Carrot");
        assertNotNull(product);
        productDao.deleteProduct(product.getId());
        Product productD = productDao.getProductById(product.getId());
        assertNull(productD);
    }

    @AfterEach
    public void closeConnection(){
        try {
            connection.close();
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot close connection in test class", e);
        }
    }

}
