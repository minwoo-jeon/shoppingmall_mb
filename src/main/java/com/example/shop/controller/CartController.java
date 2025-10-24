package com.example.shop.controller;

import com.example.shop.domain.CartDto;
import com.example.shop.domain.CartItemDto;
import com.example.shop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    public final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 상품 추가
    @PostMapping("/{userId}/items")
    public ResponseEntity<String> addCartItem(@PathVariable("userId") Long userId,
                                              @RequestParam("productId") Long productId,
                                              @RequestParam("quantity")  int quantity) {
        try {
            cartService.addCart(userId, productId, quantity);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.CONFLICT);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR); // 500-> 클라이언트 요청 자체는
        }
            return ResponseEntity.status(201).body("상품 추가 완료");
    }


    // 사용자 장바구니 상품 조회

    @GetMapping("/{userId}/items")
    public ResponseEntity<?> getCartItems(@PathVariable("userId") Long userId ){

        List<CartDto>  items  = cartService.getCartItemByUser(userId);

        return new ResponseEntity<>(items, HttpStatus.OK); // 정상 조회 시 200 반환
    }


    //사용자 장바구니 삭제

}
