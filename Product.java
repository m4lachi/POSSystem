package com.example.possystem;

public class Product {
    private String prodID;
    private String prodName;
    private double prodPrice;
    private int quantity;

    private String prodImage;

    public Product(String prodID, String prodName, double prodPrice, int quantity, String prodImage){
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.quantity = quantity;
        this.prodImage = prodImage;
    }

    public String getProdID(){
        return prodID;
    }

    public void setProdID(String newProdID){
        this.prodID = newProdID;
    }

    public String getProdName(){
        return prodName;
    }

    public void setProdName(String newProdName){
        this.prodName = newProdName;
    }

    public double getProdPrice(){
        return prodPrice;
    }
    public void setProdPrice(double newProdPrice) {
        this.prodPrice = newProdPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagePath() { return prodImage; }
}
