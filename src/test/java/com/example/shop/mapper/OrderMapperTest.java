package com.example.shop.mapper;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.OrderItemDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;


    @Test
    void testInsertAndSelectOrder() {
        //given
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(50000L);
        order.setStatus("주문준비중");
        orderMapper.insertOrder(order);

        //사용자 주문 조회
        OrderDto result = orderMapper.selectOrderDetail(34L);

        //검증
        assertThat(result).isNotNull();
    }


    @Test
    void selectOrderDetails() {
        //주문 생성
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(50000L);
        order.setStatus("주문준비중");
        orderMapper.insertOrder(order);

        Long orderId = order.getId();

        //주문 상세 상품 생성
        OrderItemDto item = new OrderItemDto();
        item.setQuantity(2);
        item.setOrderId(orderId);
        item.setProductId(1L);
        item.setProductName("와이드 코튼 팬츠");
        item.setPrice(24000L);
        orderMapper.insertOrderItem(item);

        //주문 상세 상품 생성
        OrderItemDto item2 = new OrderItemDto();
        item2.setQuantity(1);
        item2.setOrderId(orderId);
        item2.setProductId(51L);
        item2.setProductName("일자핏 슬랙스");
        item2.setPrice(25000L);
        orderMapper.insertOrderItem(item2);

        //주문 상세 조회
        OrderDto saveOrder = orderMapper.selectOrderDetail(orderId);

        System.out.println("saveOrder = " + saveOrder);


        //검증
        assertThat(saveOrder).isNotNull();
        assertThat(saveOrder.getUserId()).isEqualTo(34L);
        assertThat(saveOrder.getItems()).hasSize(2);
    }


    @Test
    @DisplayName("주문 취소")
    public void cancelOrder(){


            //주문 생성
            OrderDto order = new OrderDto();
            order.setUserId(34L);
            order.setCartId(2L);
            order.setTotalPrice(50000L);
            order.setStatus("주문준비중");
            orderMapper.insertOrder(order);

            Long orderId = order.getId();

            //주문 상세 상품 생성
            OrderItemDto item = new OrderItemDto();
            item.setQuantity(2);
            item.setOrderId(orderId);
            item.setProductId(1L);
            item.setProductName("와이드 코튼 팬츠");
            item.setPrice(24000L);
            orderMapper.insertOrderItem(item);

            //주문 상세 상품 생성
            OrderItemDto item2 = new OrderItemDto();
            item2.setQuantity(1);
            item2.setOrderId(orderId);
            item2.setProductId(51L);
            item2.setProductName("일자핏 슬랙스");
            item2.setPrice(25000L);
            orderMapper.insertOrderItem(item2);

            //주문 상세 조회
            OrderDto saveOrder = orderMapper.selectOrderDetail(orderId);
            System.out.println("saveOrder = " + saveOrder);

            //검증
            assertThat(saveOrder).isNotNull();
            assertThat(saveOrder.getUserId()).isEqualTo(34L);
            assertThat(saveOrder.getItems()).hasSize(2);


            //주문취소
            int result = orderMapper.cancelOrder(orderId);
            assertThat(result).isEqualTo(1);

            OrderDto orderDto = orderMapper.selectOrderDetail(orderId);
            assertThat(orderDto.getStatus()).isEqualTo("주문취소");

        }




    }
