package com.example.shop.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class CartDto {
    private Long id;
    private Long userId;

    private List<CartItemDto> items;

}
