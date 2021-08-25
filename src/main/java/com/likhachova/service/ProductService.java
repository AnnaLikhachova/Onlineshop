package com.likhachova.service;

import com.likhachova.dao.ProductDao;
import com.likhachova.dao.jdbc.JdbcProductDao;
import com.likhachova.model.Product;

import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService(ProductDao productDao)
    {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProduct(int id) {
        return productDao.getProductById(id);
    }

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }

    public List<Product> getProductsByDescription(String description) {
        return productDao.getProductsByDescription(description);
    }

}
