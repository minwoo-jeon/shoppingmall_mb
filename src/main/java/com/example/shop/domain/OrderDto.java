package com.example.shop.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private Long userId;
    private Long cartId;
    private String status;
    private Long totalPrice;
    private List<OrderItemDto> items;
}
