package com.likhachova.dao;

import com.likhachova.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts();

    void updateProduct(Product product);

    void addProduct(Product product);

    void deleteProduct(int id);

    Product getProductById(int id);

    Product getProductByName(String name);

    List<Product> getProductsByDescription(String description);

}
