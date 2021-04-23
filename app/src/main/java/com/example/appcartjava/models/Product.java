package com.example.appcartjava.models;

public class Product {

    private int productId;
    private int productImage;
    private String productTitle;
    private int productPrice;
    private int productAmount = 0;

    public Product() {
    }

    public Product(int productId, int productImage, String productTitle, int productPrice, int productAmount) {
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productImage=" + productImage +
                ", productTitle='" + productTitle + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productAmount=" + productAmount +
                '}';
    }
}
