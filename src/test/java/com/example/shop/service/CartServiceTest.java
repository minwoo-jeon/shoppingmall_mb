package com.example.shop.service;

import com.example.shop.domain.CartDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@Slf4j
public class CartServiceTest {

    @Autowired
    CartService cartService;



    @Test
    void cartAddTest() {

        Long userId = 37L;
        Long productId = 1l;
        int quantity = 2;

        cartService.addCart(userId, productId, quantity);

//        //해당 유저의 카드가 생성됐는지 확인
//        CartDto cart = cartService.getCartByUser(userId);
//        Assertions.assertNotNull(cart);
//        //해당 유저의 카트 아이템이 잘insert 됐는지 확인
////        assertEquals(1,cart.getItems().size());
//    }
    }
}
