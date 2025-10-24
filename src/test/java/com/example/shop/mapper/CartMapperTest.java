package com.example.shop.mapper;

import com.example.shop.domain.CartDto;
import com.example.shop.domain.CartItemDto;
import com.example.shop.domain.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class CartMapperTest {


    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;



    @Test
    void selectCartByUserId() {

        Long userId = 34L;
        CartDto cartDto = cartMapper.selectCartByUserId(userId);
        log.info("cartDto ={}" , cartDto);
        assertNotNull(cartDto); // null이 아니면 통과
    }


    @Test
    void insertCartTest(){
        Long userId = 36L;
        CartDto cartDto = cartMapper.selectCartByUserId(userId);


        //null 이어야함
        assertNull(cartDto);

        //장바구니 추가
        CartDto newCart = new CartDto();
        newCart.setUserId(userId);

        cartMapper.insertCart(newCart);

        //새로 생성한 장바구니 조회 (userId 값이 같아야함 )
        CartDto findCart = cartMapper.selectCartByUserId(userId);
        assertEquals(findCart.getUserId() , userId);
    }


    //사용자의 장바구나 아이템 조회
    @Test
    void selectCartItemTest(){
        Long userId = 34L;
        CartDto cartDto = cartMapper.selectCartByUserId(userId);

        Long productId = 1L;
        ProductDto productDto = productMapper.readProductById(productId);


       CartItemDto findCartItemDto = cartMapper.selectCartItem(cartDto.getId(), productDto.getId());

       assertEquals(findCartItemDto.getCartId() ,2);
    }

    @Test
    void insertCartItemTest(){

        CartItemDto cartItemDto = new CartItemDto(1,4L,new ProductDto());
        int cnt = cartMapper.insertCartItem(cartItemDto);
        assertEquals(cnt, 1);
    }



    @Test
    void updateCartItemTest(){
        Long cartId = 2L;
        Long prId = 1l;

        CartItemDto cartItemDto = new CartItemDto(2, 2L, new ProductDto());
        int cnt = cartMapper.updateCartItem(cartItemDto);
        assertEquals(1,1);

       CartItemDto findCartItem = cartMapper.selectCartItem(2L,1L);
       assertEquals(findCartItem.getQuantity() , 3);
    }

    @Test
    void deleteCartItemTest(){
        Long cartId = 2L;
        Long prId = 1l;

        int cnt = cartMapper.deleteCartItem(cartId, prId);
        assertEquals(cnt, 1);
    }


    @Test
    void testSelectCartByUserId() {
        Long testUserId = 34L; // 테스트할 유저 ID

        List<CartDto> carts = cartMapper.selectCartItemByUserId(testUserId);

        // 출력해서 결과 확인
        for (CartDto cart : carts) {
            System.out.println("Cart ID: " + cart.getId() + ", User ID: " + cart.getUserId());
            for (CartItemDto item : cart.getItems()) {
                ProductDto product = item.getProduct();
                System.out.println("  Item ID: " + item.getId() +
                        ", Quantity: " + item.getQuantity() +
                        ", Product Name: " + product.getProductName() +
                        ", Price: " + product.getPrice() +
                        ", Stock: " + product.getStock());
            }
        }

        // 간단한 assertion 예시
        assert !carts.isEmpty() : "장바구니가 비어있습니다!";
        assert carts.get(0).getItems().get(0).getProduct().getId() != null : "상품 정보 없음!";
    }

}