package com.example.appcartjava.models;

public class Product {

    private int productImage;
    private String productTitle;
    private String productPrice;
    private Boolean isAddToCart;

    public Product() {
    }

    public Product(int productImage, String productTitle, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }

    public Boolean getAddToCart() {
        return isAddToCart;
    }

    public void setAddToCart(Boolean addToCart) {
        isAddToCart = addToCart;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
