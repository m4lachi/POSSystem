package com.example.possystem;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Inventory {
    private List<Product> productList;

    public Inventory() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
        System.out.println("Product added: " + product.getProdName());
    }

    public void removeProduct(String productId) {
        Product productToRemove = findProductById(productId);
        if (productToRemove != null) {
            productList.remove(productToRemove);
            System.out.println("Product removed: " + productToRemove.getProdName());
        } else {
            System.out.println("Product not found with ID: " + productId);
        }
    }

    public Product findProductById(String productId) {
        for (Product product : productList) {
            if (product.getProdID().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void displayInventory() {
        if (productList.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Product product : productList) {
                System.out.println("ID: " + product.getProdID() +
                        ", Name: " + product.getProdName() +
                        ", Price: $" + product.getProdPrice() +
                        ", Quantity: " + product.getQuantity());
            }
        }
    }

    public boolean isInStock(String productId) {
        Product product = findProductById(productId);
        return product != null && product.getQuantity() > 0;
    }

    public void updateProductQuantity(String productId, int quantitySold) {
        Product product = findProductById(productId);
        if (product != null) {
            if (quantitySold <= product.getQuantity()) {
                int newQuantity = product.getQuantity() - quantitySold;
                product.setQuantity(newQuantity);
                System.out.println("Updated quantity of " + product.getProdName() + " to " + newQuantity);
            } else {
                System.out.println("Not enough stock for " + product.getProdName() + ". Available: " + product.getQuantity());
            }
        } else {
            System.out.println("Product not found with ID: " + productId);
        }
    }

    public List<Product> getProductList() {
        return Collections.unmodifiableList(productList);
    }
}
