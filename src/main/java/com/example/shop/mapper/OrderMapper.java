package com.example.shop.mapper;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.OrderItemDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;

@Mapper
public interface OrderMapper {
    //주문추가
    void insertOrder(OrderDto order);

    //주문 항목 추가
    void insertOrderItem(OrderItemDto orderItem);

    //주문조회
    OrderDto selectOrderDetail(Long orderId);

    //주문취소
    int cancelOrder(Long OrderId);

}
