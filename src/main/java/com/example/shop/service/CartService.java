            package com.example.shop.service;

            import com.example.shop.domain.CartDto;
            import com.example.shop.domain.CartItemDto;
            import com.example.shop.domain.ProductDto;
            import com.example.shop.mapper.CartMapper;
            import lombok.extern.slf4j.Slf4j;
            import org.springframework.stereotype.Service;
            import org.springframework.transaction.annotation.Transactional;
            import java.util.*;
            @Service
            @Slf4j
            public class CartService {

                private final CartMapper cartMapper;

                public CartService(CartMapper cartMapper) {
                    this.cartMapper = cartMapper;
                }

                @Transactional
                public void addCart(Long userId, Long productId, int quantity) {
                    // 1️. 상품 존재 여부 확인
                    ProductDto product = cartMapper.selectProductById(productId); // 상품 조회
                    if (product == null) {
                        throw new IllegalArgumentException("존재하지 않는 상품입니다.");
                    }

                    // 2️. 재고 확인
                    if (product.getStock() < quantity) {
                        throw new IllegalStateException("재고가 부족합니다.");
                    }

                    // 3️ 유저 장바구니 조회
                    CartDto cartDto = cartMapper.selectCartByUserId(userId);

                    // 4️. 장바구니 없으면 생성
                    if (cartDto == null) {
                        cartDto = new CartDto();
                        cartDto.setUserId(userId);
                        cartMapper.insertCart(cartDto);
                    }

                    // 5️. 장바구니 아이템 조회
                    CartItemDto existingItem = cartMapper.selectCartItem(cartDto.getId(), productId);

                    if (existingItem == null) {
                        // 6.  새 아이템 추가
                        CartItemDto newCartItem = new CartItemDto();
                        newCartItem.setCartId(cartDto.getId());
                        newCartItem.setQuantity(quantity);
                        newCartItem.setProduct(product);
                        cartMapper.insertCartItem(newCartItem);
                    } else {
                        // 7️. 기존 아이템 수량 증가
                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                        cartMapper.updateCartItem(existingItem);
                    }
                }




                //해당 유저 장바구니 아이템 리스트
                public List<CartDto> getCartItemByUser(Long userId){
                   List<CartDto> cartsItems =  cartMapper.selectCartItemByUserId(userId);
                   return cartsItems;
                }




                //장바구니 삭제
                public void removeCartItem(Long userId , Long productId){

                    CartDto cartDto = cartMapper.selectCartByUserId(userId);

                    if(cartDto != null)
                        cartMapper.deleteCartItem(userId, productId);
                }
            }
