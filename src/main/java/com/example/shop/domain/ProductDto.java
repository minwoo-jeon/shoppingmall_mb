package com.example.shop.domain;

import java.util.Objects;

public class ProductDto {
    private Long id;
    private String productName;
    private int price;
    private String description;
    private String productImg;

    private int stock;


    public ProductDto() {
    }

    public ProductDto(String productName, int price, String description, String productImg, int stock) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.productImg = productImg;
        this.stock = stock;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return price == that.price && stock == that.stock && Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(description, that.description) && Objects.equals(productImg, that.productImg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, price, description, productImg, stock);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", productImg='" + productImg + '\'' +
                ", stock=" + stock +
                '}';
    }
}