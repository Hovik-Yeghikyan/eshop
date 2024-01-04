package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Category;
import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryManager categoryManager = new CategoryManager();

    public void add(Product product) {
        String query = "INSERT INTO product (name,description,price,quantity,category_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getCategory().getId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        String query = "SELECT * from product";
        List<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int categoryId = resultSet.getInt("category_id");
                Category category = categoryManager.getCategoryById(categoryId);
                Product product = new Product(id, name, description, price, quantity, category);
                result.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int categoryId = resultSet.getInt("category_id");
                Category category = categoryManager.getCategoryById(categoryId);
                return new Product(id, name, description, price, quantity, category);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        String query = "SELECT * from product WHERE category_id=" + categoryId;
        List<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                Category category = categoryManager.getCategoryById(categoryId);
                Product product = new Product(id, name, description, price, quantity, category);
                result.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteProductById(int id) {
        if (getProductById(id) == null) {
            System.out.println("Product with " + id + " id does not exists!");
            return;
        }
        String sql = "DELETE FROM product WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Product was removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductByCategory(int categoryId) {
        if (categoryManager.getCategoryById(categoryId)==null){
            System.out.println("Category with " + categoryId + " not found!");
            return;
        }
        String sql = "DELETE FROM product WHERE category_id=" + categoryId;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Products was removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        if (getProductById(product.getId()) == null) {
            System.out.println("Product with " + product.getId() + " ID does not exists");
            return;
        }
        String query = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getCategory().getId());
            ps.setInt(6, product.getId());
            ps.executeUpdate();
            System.out.println("Product Updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSumOfProducts() {
        String sql = "SELECT SUM(quantity) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int sum = resultSet.getInt(1);
                return sum;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMaxPriceOfProducts() {
        String sql = "SELECT MAX(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double sum = resultSet.getInt(1);
                return sum;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMinPriceOfProducts() {
        String sql = "SELECT MIN(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double sum = resultSet.getInt(1);
                return sum;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getAVGPriceOfProducts() {
        String sql = "SELECT AVG(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double sum = resultSet.getInt(1);
                return sum;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
