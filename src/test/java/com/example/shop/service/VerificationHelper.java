package com.example.shop.service;

import com.example.shop.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Component
public class VerificationHelper {
    @Autowired
    private OrderMapper orderMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void assertOrderNotExist(Long orderId) {
        assertThat(orderMapper.selectOrderDetail(orderId)).isNull();
    }
}
