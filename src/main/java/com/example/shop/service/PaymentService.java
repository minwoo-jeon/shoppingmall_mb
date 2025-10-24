package com.example.shop.service;

import com.example.shop.domain.PaymentDto;
import com.example.shop.mapper.PaymentMapper;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {


    public final PaymentMapper paymentMapper;


    //외부 모듈과 연동하고 결제데이터를 남김.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processPayment(Long orderId,Long amount) {
        if (amount > 1000) {
            throw new IllegalStateException("결제 금액 초과로 실패");
        }
        else {
            paymentMapper.insertPaymentLog(orderId,"PAY_SC");
        }
    }
}
