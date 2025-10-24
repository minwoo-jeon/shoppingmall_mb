package com.example.shop.domain;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private int quantity;
    private Long orderId;
    private Long productId;
    private String productName;
    private Long price;
}
