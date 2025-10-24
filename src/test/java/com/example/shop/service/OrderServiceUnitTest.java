package com.example.shop.service;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.OrderItemDto;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class OrderServiceUnitTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createOrder_Success(){
        //given 주문 DTO
        OrderItemDto item = new OrderItemDto();
        item.setProductId(1L);
        item.setPrice(24000l);
        item.setQuantity(2);

        OrderDto order = new OrderDto();
        order.setItems(List.of(item));
        order.setTotalPrice(48000L);

        //Mocking 설정
        doNothing().when(orderMapper).insertOrder(order);
        doNothing().when(orderMapper).insertOrderItem(item);
        when(productMapper.decreaseStock(1L, 2)).thenReturn(1);



        // when: 주문 생성
        orderService.createOrder(order);

        // then: 서비스 로직 호출 확인
        verify(orderMapper).insertOrder(order);
        verify(orderMapper).insertOrderItem(item);
        verify(productMapper).decreaseStock(1L, 2);
    }

    @Test
    void createOrder_StockShortage() {
        // given: 재고 부족 상황
        OrderItemDto item = new OrderItemDto();
        item.setProductId(1L);
        item.setPrice(24000L);
        item.setQuantity(51);

        OrderDto order = new OrderDto();
        order.setItems(List.of(item));
        order.setTotalPrice(1224000L);

        // Mocking: 재고 부족 상황
        doNothing().when(orderMapper).insertOrder(order);
        doNothing().when(orderMapper).insertOrderItem(item);
        when(productMapper.decreaseStock(1L, 51)).thenReturn(0); // 재고 부족

        // then: 예외 발생 확인
        assertThrows(IllegalStateException.class, () -> orderService.createOrder(order));
    }

//    @Test
//    void createOrder_TotalPriceMismatch() {
//        // given: 클라이언트 totalPrice와 실제 합산 금액 불일치
//        OrderItemDto item = new OrderItemDto();
//        item.setProductId(1L);
//        item.setPrice(10000);
//        item.setQuantity(2);
//
//        OrderDto order = new OrderDto();
//        order.setItems(List.of(item));
//        order.setTotalPrice(15000); // 실제 합산 금액 20000과 불일치
//
//        // then: 예외 발생 확인
//        assertThrows(IllegalStateException.class, () -> orderService.createOrder(order));
//    }

}