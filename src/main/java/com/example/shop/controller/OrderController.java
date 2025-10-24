package com.example.shop.controller;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.OrderItemDto;
import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    //1.주문 생성
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {

        try {
            Long orderId = orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("주문 생성 완료. 주문 ID:" + orderId);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버오류 발생" +  e);
        }
    }

    //주문 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long orderId) {
        try {
            OrderDto orderDto = orderService.getOrderDetails(orderId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(orderDto);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 에러" + e);
        }
    }

    //주문 취소
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("주문 취소 완료. 주문 번호:" + orderId);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e);
        }
    }
}