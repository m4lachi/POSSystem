package com.example.possystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class POSSystemGUI extends Application {

    private Inventory inventory = new Inventory();
    private Sales sales;

    private ListView<String> productListView; // ListView for product selection
    private TextField quantityField;
    private Label totalLabel;
    private VBox layout;
    private TextArea cartDisplay;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize inventory and sales system
        sales = new Sales(inventory);
        setupSampleInventory();

        // Create GUI components
        productListView = new ListView<>(); // Initialize the ListView
        quantityField = new TextField();
        totalLabel = new Label("Total: $0.00");
        cartDisplay = new TextArea();
        cartDisplay.setEditable(false);

        // Create Buttons
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(e -> handleAddToCart());

        Button processSaleButton = new Button("Process Sale");
        processSaleButton.setOnAction(e -> handleProcessSale());

        // Layout setup
        layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        HBox inputFields = new HBox(10);
        inputFields.getChildren().addAll(new Label("Quantity:"), quantityField, addToCartButton);

        layout.getChildren().addAll(new Label("Products"), productListView, inputFields, totalLabel, processSaleButton);

        // Set up and display the scene
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pizza POS System");
        primaryStage.show();

        // Display initial inventory
        updateInventoryDisplay();
    }

    private void setupSampleInventory() {
        inventory.addProduct(new Product("100", "Small Plain Pizza", 12.74, 500,"C:\\Users\\miche\\CS Files\\POSsystem\\productIcons\\ezgif-3-cd9f9ae764.jpg"));
        /*inventory.addProduct(new Product("110", "Medium Plain Pizza", 15.49, 500));
        inventory.addProduct(new Product("120", "Large Plain Pizza", 18.49, 500));
        inventory.addProduct(new Product("130", "Extra Large Plain Pizza", 21.74, 500));
        inventory.addProduct(new Product("101", "Small Pepperoni Plain Pizza", 12.74, 600));
        inventory.addProduct(new Product("111", "Medium Pepperoni Plain Pizza", 15.49, 600));
        inventory.addProduct(new Product("121", "Large Pepperoni Plain Pizza", 18.49, 600));
        inventory.addProduct(new Product("131", "Extra Pepperoni Large Plain Pizza", 21.74, 600));
        inventory.addProduct(new Product("102", "Small Sausage Plain Pizza", 12.74, 600));
        inventory.addProduct(new Product("112", "Medium Sausage Plain Pizza", 15.49, 600));
        inventory.addProduct(new Product("122", "Large Sausage Plain Pizza", 18.49, 600));
        inventory.addProduct(new Product("132", "Extra Sausage Large Plain Pizza", 21.74, 600));*/
    }

    // Update inventory display in ListView
    private void updateInventoryDisplay() {
        productListView.getItems().clear(); // Clear previous items
        for (Product product : inventory.getProductList()) {
            productListView.getItems().add(product.getProdName() + " | Price: $" + product.getProdPrice() + " | Quantity: " + product.getQuantity());
        }

        // Create a VBox for product images
        VBox imageLayout = new VBox();
        for (Product product : inventory.getProductList()) {
            try {
                Image image = new Image(new FileInputStream(product.getImagePath())); // Load the image
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Set the desired width
                imageView.setPreserveRatio(true); // Maintain the aspect ratio
                imageLayout.getChildren().add(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Handle missing image scenario (optional)
            }
        }

        // Add the image layout to the GUI
        layout.getChildren().add(imageLayout); // Add this layout to your main layout
    }


    // Update cart display
    private void updateCartDisplay() {
        StringBuilder sb = new StringBuilder();
        double total = 0;
        for (Product product : sales.getCart()) {
            sb.append("ID: ").append(product.getProdID()).append(" | ").append(product.getProdName()).append(" | Quantity: ").append(product.getQuantity()).append("\n");
            total += product.getProdPrice() * product.getQuantity();
        }
        cartDisplay.setText(sb.toString());
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    // Handle adding a product to the cart
    private void handleAddToCart() {
        String selectedProduct = productListView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Please select a product from the list.");
            return;
        }

        String productId = selectedProduct.split(" \\| ")[0].split(": ")[1]; // Extract product ID from selected string
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity");
            return;
        }

        sales.addToCart(productId, quantity);
        updateCartDisplay();
    }

    // Handle processing the sale
    private void handleProcessSale() {
        sales.processSale();
        updateCartDisplay();
        updateInventoryDisplay();
    }

    // Show alert box for errors or warnings
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}
