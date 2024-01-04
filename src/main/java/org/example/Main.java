package org.example;

import org.example.commands.Commands;
import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;
import org.example.model.Category;
import org.example.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Commands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printAllCommands();
            String command = SCANNER.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;

                case EDIT_CATEGORY_BY_ID:
                    editCategoryById();
                    break;

                case DELETE_CATEGORY_BY_ID:
                    deleteCategoryById();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT_BY_ID:
                    editProductById();
                    break;
                case DELETE_PRODUCT_BY_ID:
                    deleteProductById();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    System.out.println("All product quantity is: " + PRODUCT_MANAGER.getSumOfProducts());
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCTS:
                    System.out.println("Maximum price of products is: " + PRODUCT_MANAGER.getMaxPriceOfProducts());
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCTS:
                    System.out.println("Minimum price of products is: " + PRODUCT_MANAGER.getMinPriceOfProducts());
                    break;
                case PRINT_AVG_OF_PRICE_PRODUCTS:
                    System.out.println("AVG price of products is: " + PRODUCT_MANAGER.getAVGPriceOfProducts());
                    break;
                default:
                    System.out.println("Wrong command, Try again!");
                    break;
            }

        }

    }

    private static void deleteProductById() {
        List<Product> products = PRODUCT_MANAGER.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
        try {
            System.out.println("Please input product ID for DELETE");
            int id = Integer.parseInt(SCANNER.nextLine());
            PRODUCT_MANAGER.deleteProductById(id);
        } catch (NumberFormatException e) {
            System.out.println("You choose incorrect format");
        }

    }

    private static void editProductById() {
        List<Product> products = PRODUCT_MANAGER.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
        try {
            System.out.println("Please input product ID for Update");
            int id = Integer.parseInt(SCANNER.nextLine());
            if (PRODUCT_MANAGER.getProductById(id) == null) {
                System.out.println("Wrong ID!!!");
                return;
            }
            System.out.println("Please input new name for Product");
            String name = SCANNER.nextLine();
            System.out.println("Please input new description for Product");
            String description = SCANNER.nextLine();
            System.out.println("Please input new price for Product");
            double price = Double.parseDouble(SCANNER.nextLine());
            System.out.println("Please input new quantity for Product");
            int quantity = Integer.parseInt(SCANNER.nextLine());
            System.out.println("Please choose new category for Product");
            List<Category> categories = CATEGORY_MANAGER.getAllCategories();
            for (Category category : categories) {
                System.out.println(category);
            }
            int category_id = Integer.parseInt(SCANNER.nextLine());
            if (CATEGORY_MANAGER.getCategoryById(category_id) == null) {
                System.out.println("Category not found");
                return;
            }
            Category category = CATEGORY_MANAGER.getCategoryById(category_id);
            Product product = new Product(id, name, description, price, quantity, category);
            PRODUCT_MANAGER.updateProduct(product);

        } catch (NumberFormatException e) {
            System.out.println("You choose incorrect format");
        }
    }

    private static void addProduct() {
        System.out.println("Please input name for Product");
        String name = SCANNER.nextLine();
        System.out.println("Please input description for Product");
        String description = SCANNER.nextLine();
        try {
            System.out.println("Please input price for Product");
            double price = Double.parseDouble(SCANNER.nextLine());
            System.out.println("Please input quantity for Product");
            int quantity = Integer.parseInt(SCANNER.nextLine());
            System.out.println("Please choose category for Product");
            List<Category> categories = CATEGORY_MANAGER.getAllCategories();
            for (Category category : categories) {
                System.out.println(category);
            }
            int category_id = Integer.parseInt(SCANNER.nextLine());
            if (CATEGORY_MANAGER.getCategoryById(category_id) == null) {
                System.out.println("Category not found");
                return;
            }
            Category category = CATEGORY_MANAGER.getCategoryById(category_id);
            PRODUCT_MANAGER.add(Product.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .category(category)
                    .build());
            System.out.println("Product created!");
        } catch (NumberFormatException e) {
            System.out.println("You choose incorrect format");
        }
    }


    private static void deleteCategoryById() {
        List<Category> categories = CATEGORY_MANAGER.getAllCategories();
        for (Category category : categories) {
            System.out.println(category);
        }
        try {
            System.out.println("Please input category ID you want DELETE");
            int id = Integer.parseInt(SCANNER.nextLine());
            List<Product> products = PRODUCT_MANAGER.getProductsByCategoryId(id);
            if (products.isEmpty()) {
                CATEGORY_MANAGER.deleteCategoryById(id);
            } else {
                System.out.println("There are " + products.size() + " books. Remove all books? YES/NO?");
                String answer = SCANNER.nextLine();
                if ("YES".equalsIgnoreCase(answer)) {
                    PRODUCT_MANAGER.deleteProductByCategory(id);
                    CATEGORY_MANAGER.deleteCategoryById(id);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Incorrect format for category ID!!!");
        }

    }

    private static void editCategoryById() {
        List<Category> categories = CATEGORY_MANAGER.getAllCategories();
        for (Category category : categories) {
            System.out.println(category);
        }
        try {
            System.out.println("Please input category ID for Update");
            int id = Integer.parseInt(SCANNER.nextLine());
            System.out.println("Please input name for Update");
            String name = SCANNER.nextLine();
            Category category = new Category(id, name);
            CATEGORY_MANAGER.updateCategory(category);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format for category ID!!!");
        }
    }

    private static void addCategory() {
        System.out.println("Please input name for Category");
        String name = SCANNER.nextLine();
        Category category = new Category(name);
        CATEGORY_MANAGER.add(category);
        System.out.println("Category created!");
    }
}