package com.example.shop.service;

import com.example.shop.domain.*;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    public final OrderMapper orderMapper;
    public final ProductMapper productMapper;
    public final PaymentService paymentService;//결제 로그
    @Transactional
    public Long createOrder(OrderDto orderDto) {

        //1.총 금액 계산
        int calculatedTotal = 0;
        for (OrderItemDto item : orderDto.getItems()) {
            calculatedTotal += item.getPrice() * item.getQuantity();
        }

        //2. 총액 검증
        if (calculatedTotal != orderDto.getTotalPrice()) {
            throw new IllegalStateException("총 금액 불일치");
        }

        // 1. 주문 생성
        saveOrder(orderDto);

        // 2. 주문 아이템 저장 및 재고 차감
        saveOrderItemsAndDecreaseStock(orderDto);

        // 3. 결제 처리 (별도 트랜잭션)
        processPayment(orderDto);

        return orderDto.getId();
    }

    private void processPayment(OrderDto orderDto) {
        try {
            paymentService.processPayment(orderDto.getId(), orderDto.getTotalPrice());
        } catch (IllegalStateException e) {
            log.info("결체 처리 실패:{}" , e.getMessage());
        }
    }


    //결제 처리 트랜잭션 분리


    private void saveOrderItemsAndDecreaseStock(OrderDto orderDto) {
        for (OrderItemDto items : orderDto.getItems()) {
            items.setOrderId(orderDto.getId());
            orderMapper.insertOrderItem(items);

            //1.해당 상품 id , 제고 수량 값을 가져온다
            Long productId = items.getProductId();
            int stockNum = items.getQuantity();


            // 동시성 조건부 update
            int updated = productMapper.decreaseStock(productId, stockNum);
            if (updated == 0) {
                throw new IllegalStateException("재고가 부족합니다");
            }
        }
    }


    public void saveOrder(OrderDto orderDto) {
        orderDto.setStatus("PAYMENT_WAITING");
        orderMapper.insertOrder(orderDto);
        log.info("주문 생성 완료 - orderId: {}", orderDto.getId());
    }



    //2. 주문조회
    public OrderDto getOrderDetails(Long orderId) {
        OrderDto orderDto = orderMapper.selectOrderDetail(orderId);

        if (orderDto == null) {
            throw new IllegalArgumentException("해당 주문이 존재하지 않습니다. orderId=" + orderId);
        }
        return orderDto;
    }


    @Transactional
   //3. 주문 취소
   public void cancelOrder(Long orderId){

        //1. 주문을 조회한다
       OrderDto cancelOrder = orderMapper.selectOrderDetail(orderId);
       if (cancelOrder == null) {
           throw new IllegalStateException("주문을 찾을수없습니다. 주문번호:" + orderId);
       }

       //2. 이미 배송시작 이후면 취소 불가.
       String status = cancelOrder.getStatus();
       if(status.equals("DELIVERY")){
           throw new IllegalStateException("배송중인 상품은 취소할수 없습니다");
       }

       //3. 주문 취소
        orderMapper.cancelOrder(orderId);
       
        
       //4.상품 재고 복원
        for (OrderItemDto item : cancelOrder.getItems()) {
            productMapper.changeStock(item.getProductId(), item.getQuantity());
        }
   }
}
