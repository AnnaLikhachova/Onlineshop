package com.likhachova.dao;

import com.likhachova.dao.jdbc.JdbcProductDao;
import com.likhachova.model.Product;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDaoTest {

    @Mock
    private ProductDao productDao;

    @Before
    public void set() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:~/testdata");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        assertNotNull(dataSource);
        productDao = new JdbcProductDao(dataSource);
    }

    @Test
    @DisplayName("Get all products")
    public void test_DaoReturnListOfProducts() {
        Iterable<Product> products = productDao.getAllProducts();
        assertNotNull(products);
    }

    @Test
    @DisplayName("Add product")
    public void test_DaoAddProduct() {
        Product product = new Product("Apples green", 60, "Green Apples from Canada", LocalDate.of(2021, 8, 8));
        productDao.addProduct(product);
        Product productD = productDao.getProductByName("Apples green");
        assertNotNull(product);
        assertEquals(product.getName(), productD.getName());
        assertEquals(product.getPrice(), productD.getPrice());
        assertEquals(product.getDescription(), productD.getDescription());
        assertEquals(product.getDate(), productD.getDate());
        productDao.deleteProduct(productD.getId());
    }

    @Test
    @DisplayName("Update product")
    public void test_DaoUpdateProduct() {
        Product testProduct = new Product("Bananas", 60, "Green Bananas", LocalDate.of(2021, 8, 8));
        productDao.addProduct(testProduct);
        Product product = productDao.getProductByName("Bananas");
        assertNotNull(product);
        product.setName("Carrots");
        product.setPrice(90);
        product.setDate(LocalDate.of(2021, 8, 7));
        productDao.updateProduct(product);
        Product updatedProduct = productDao.getProductByName("Carrots");
        assertNotNull(product);
        assertEquals(product.getName(), updatedProduct.getName());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getDescription(), updatedProduct.getDescription());
        assertEquals(product.getDate(), updatedProduct.getDate());
        productDao.deleteProduct(updatedProduct.getId());
    }

    @Test
    @DisplayName("Delete product")
    public void test_DaoDeleteProduct() {
        Product testProduct = new Product("Tomatoes", 60, "Tomatoes from Canada", LocalDate.of(2021, 8, 8));
        productDao.addProduct(testProduct);
        Product product = productDao.getProductByName("Tomatoes");
        assertNotNull(product);
        productDao.deleteProduct(product.getId());
    }
}
