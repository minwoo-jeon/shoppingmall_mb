package com.example.shop.mapper;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.PaymentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {

    //결제 로그 추가
    public int insertPaymentLog(@Param("orderId") Long orderId ,@Param("status") String status);

    //결제 로그 조회
    public PaymentDto selectPaymentLogByOrderId(Long OrderId);

}
