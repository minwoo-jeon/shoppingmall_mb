package com.example.shop.mapper;

import com.example.shop.domain.CartDto;
import com.example.shop.domain.CartItemDto;
import com.example.shop.domain.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

@Mapper
public interface CartMapper {

    // //사용자 장바구니 존재 여부확인
    CartDto selectCartByUserId(Long userid);

    List<CartDto> selectCartItemByUserId(Long userid);


    //장바구니 생성
    void insertCart(CartDto cartDto);


    //장바구니 아이템 존재 여부 확인 (상품 중복 체크)
    CartItemDto selectCartItem(@Param("cartId") Long cartId , @Param("productId") Long productId);


    //장바구니 아이템 추가
    int insertCartItem(CartItemDto cartItemDto);


    //장바구니 아이템 수량 증가
    int updateCartItem(CartItemDto cartItemDto);

    //장바구니 삭제
    int deleteCartItem(@Param("cartId") Long cartId , @Param("productId") Long productId);

    //상품이 존재하는지 ?
    ProductDto selectProductById(Long productId);
}
