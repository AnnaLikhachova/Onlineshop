package com.likhachova.dao.jdbc;

import com.likhachova.dao.ProductDao;
import com.likhachova.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM products;";
        return getAllProducts(query);
    }

    @Override
    public void updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, price = ?, description= ?, date = ? WHERE id = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDate(4, Date.valueOf(product.getDate()));
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot update product in database", e);
        }
    }

    @Override
    public void addProduct(Product product) {
        String query = "INSERT INTO products (name, price, description, date) Values (?, ?, ?, ?);";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDate(4, Date.valueOf(product.getDate()));
            preparedStatement.execute();
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot add product to database", e);
        }
    }

    @Override
    public void deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot delete product from database", e);
        }
    }

    @Override
    public Product getProductById(int id) {
        String query = "SELECT id, name, description, price, date FROM products WHERE id = ?;";
        Product product;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                product = getProductFromResultSet(resultSet);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot get product from database", e);
        }
        return product;
    }

    @Override
    public Product getProductByName(String name) {
        String query = "SELECT id, name, price, date FROM products WHERE name = ?;";
        Product product;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            preparedStatement.setString(1, name);
            product = getProductFromResultSet(resultSet);
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot get product from database", e);
        }
        return product;
    }

    @Override
    public List<Product> getProductsByDescription(String description) {
        String query = "SELECT * FROM products WHERE description LIKE ? OR name LIKE ?;";
        List<Product> products;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, "%"+description+"%");
            preparedStatement.setString(2, "%"+description+"%");
            try( ResultSet resultSet = preparedStatement.executeQuery();){
                products = getAllProductsByDescriptionFromResultSet(resultSet);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot get product from database", e);
        }
        return products;
    }

    private List<Product> getAllProductsByDescriptionFromResultSet(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while(resultSet.next()) {
            int productId = resultSet.getInt("id");
            String productName = resultSet.getString("name");
            int price = resultSet.getInt("price");
            String productDescription = resultSet.getString("description");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            products.add(new Product(productId, productName, price, productDescription, date));
        }
        return products;
    }

    private List<Product> getAllProducts(String query) {
        List<Product> products;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            products = getAllProductsByDescriptionFromResultSet(resultSet);
        }
        catch(SQLException e) {
            throw new RuntimeException("Cannot get all products from database", e);
        }
        return products;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product;
        if(!resultSet.next()) {
            throw new IllegalArgumentException("No product in database");
        }
        int productId = resultSet.getInt("id");
        String productName = resultSet.getString("name");
        int price = resultSet.getInt("price");
        String productDescription = resultSet.getString("description");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        product = new Product(productId, productName, price, productDescription, date);
        if(resultSet.next()) {
            throw new IllegalArgumentException("More than one product in database");
        }
        return product;
    }
}
