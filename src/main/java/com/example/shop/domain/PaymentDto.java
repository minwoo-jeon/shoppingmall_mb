package com.example.shop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDto {

    private Long id;
    private Long orderId;
    private String status;
    private LocalDateTime logTime;
}
