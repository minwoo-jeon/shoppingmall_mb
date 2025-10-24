package com.example.shop.mapper;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.OrderItemDto;
import com.example.shop.domain.PaymentDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PaymentMapperTest {

    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    OrderMapper orderMapper;

    @Test
    @DisplayName("결제 로그 저장")
    void test(){
        // 1. 테스트용 주문 객체 생성 및 저장
        //주문 생성
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(50000L);
        order.setStatus("WT_PAYMENT");
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


        //2. 저장된 주문의 ID로 결제 로그생성

        String status = "PAY_SU";

        int result =paymentMapper.insertPaymentLog(orderId , status);

        //주문 상세 조회 및 검증
        OrderDto saveOrder = orderMapper.selectOrderDetail(orderId);
        assertThat(saveOrder).isNotNull();
        assertThat(saveOrder.getUserId()).isEqualTo(34L);
        assertThat(saveOrder.getItems()).hasSize(1);

        // 결제 로그 저장 검증
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("결제 로그 조회")
    void selectPayment_logTest(){
        // 1. 테스트용 주문 객체 생성 및 저장
        //주문 생성
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(50000L);
        order.setStatus("WT_PAYMENT");
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


        //2. 저장된 주문의 ID로 결제 로그생성

        String status = "PAY_SU";

        int result =paymentMapper.insertPaymentLog(orderId,status);

       PaymentDto  findPayment=  paymentMapper.selectPaymentLogByOrderId(orderId);
        assertThat(findPayment.getOrderId()).isEqualTo(orderId);
        assertThat(findPayment.getStatus()).isEqualTo("PY_LOGGED");

    }
}
