package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void add(Category category) {
        String query = "INSERT INTO category(name) VALUES(?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                category.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Category> getAllCategories() {
        String query = "SELECT * FROM category";
        List<Category> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category(id, name);
                result.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                return new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCategoryById(int id) {
        if (getCategoryById(id) == null) {
            System.out.println("Category with " + id + " id does not exists!");
            return;
        }
        String sql = "DELETE FROM category WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Category was removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        if (getCategoryById(category.getId()) == null) {
            System.out.println("Category with " + category.getId() + " ID does not exists");
            return;
        }
        String query = "UPDATE category SET name = ? WHERE id = ?";
        try (PreparedStatement ps= connection.prepareStatement(query)) {
           ps.setString(1,category.getName());
           ps.setInt(2,category.getId());
           ps.executeUpdate();
            System.out.println("Category Updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
