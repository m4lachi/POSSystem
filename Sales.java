package com.example.possystem;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class Sales extends Inventory {
    private List<Product> cart;
    private Inventory inventory;

    public Sales(Inventory inventory) {
        this.cart = new ArrayList<>();
        this.inventory = inventory;
    }

    // Add a product to the sales cart by product ID
    public void addToCart(String productId, int quantity) {
        Product product = inventory.findProductById(productId);
        if (product != null && product.getQuantity() >= quantity) {
            Product cartProduct = new Product(productId, product.getProdName(), product.getProdPrice(), quantity, product.getImagePath());
            cart.add(cartProduct);
            System.out.println(quantity + " units of " + product.getProdName() + " added to the cart.");
        } else {
            System.out.println("Product not found or insufficient quantity.");
        }
    }

    // Calculate the total price of all products in the cart
    public double calculateTotal() {
        double total = 0;
        for (Product product : cart) {
            total += product.getProdPrice() * product.getQuantity();
        }
        return total;
    }

    // Process the sale and update the inventory
    public void processSale() {
        double totalAmount = calculateTotal();
        System.out.println("Total amount: $" + totalAmount);

        // Update the inventory by reducing product quantities
        for (Product product : cart) {
            inventory.updateProductQuantity(product.getProdID(), product.getQuantity());
        }

        // Clear the cart after the sale
        cart.clear();
        System.out.println("Sale completed. Inventory updated.");
    }

    // Display the current cart items
    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            System.out.println("Cart:");
            for (Product product : cart) {
                System.out.println("ID: " + product.getProdID() +
                                   ", Name: " + product.getProdName() +
                                   ", Quantity: " + product.getQuantity() +
                                   ", Price: $" + product.getProdPrice());
            }
        }
    }
    public List<Product> getCart() {
        return Collections.unmodifiableList(cart);
    }
}
