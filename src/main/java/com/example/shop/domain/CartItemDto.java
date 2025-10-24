package com.example.shop.domain;


import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private int quantity;
    private Long cartId;
    private Long productId;
    private ProductDto product; //상품객체

    public CartItemDto() {
    }

    public CartItemDto(int quantity, Long cartId, ProductDto product) {
        this.quantity = quantity;
        this.cartId = cartId;
        this.product = product;
    }
}
