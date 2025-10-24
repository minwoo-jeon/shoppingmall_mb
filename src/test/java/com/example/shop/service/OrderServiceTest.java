package com.example.shop.service;

import com.example.shop.domain.OrderDto;
import com.example.shop.domain.OrderItemDto;
import com.example.shop.domain.PaymentDto;
import com.example.shop.domain.ProductDto;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.mapper.PaymentMapper;
import com.example.shop.mapper.ProductMapper;
import jakarta.validation.constraints.Max;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    VerificationHelper verificationHelper;

    @Autowired
    PaymentMapper paymentMapper;

    @Test
    @DisplayName("주문 처리")
    public void createOrder_Success() {
        //given 상품 등록
        ProductDto productDto = new ProductDto();
        productDto.setProductName("테스트 상품");
        productDto.setPrice(10000);
        productDto.setStock(10);
        productDto.setProductImg("tset@naver.com");
        productDto.setDescription("테스트 상품입니다.");
        productMapper.insertProduct(productDto);


        //주문 아이템
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setQuantity(2);
        orderItemDto.setProductName("테스트 상품");
        orderItemDto.setPrice(20000L);
        orderItemDto.setProductId(productDto.getId());

        //주문
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(orderItemDto.getPrice() * orderItemDto.getQuantity());
        order.setStatus("주문 준비중");
        order.setItems(List.of(orderItemDto));

        // when
        Long createOrder = orderService.createOrder(order);


        //then
        OrderDto savedOrder = orderMapper.selectOrderDetail(createOrder);
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isEqualTo(createOrder);
        assertThat(savedOrder.getTotalPrice()).isEqualTo(order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 실패")
    public void createOrder_FailTest() {
        //given:재고 부족 상품 등록
        ProductDto lowStockProduct = new ProductDto();
        lowStockProduct.setProductName("남성용 후드집업");
        lowStockProduct.setPrice(10000);
        lowStockProduct.setStock(4);
        lowStockProduct.setProductImg("tset11@naver.com");
        lowStockProduct.setDescription("테스트 상품입니다.");
        productMapper.insertProduct(lowStockProduct);


        //when: 재고 부족 상품 주문
        OrderItemDto item = new OrderItemDto();
        item.setQuantity(5);
        item.setProductName("남성용 후드집업");
        item.setPrice(10000L);
        item.setProductId(lowStockProduct.getId());


        //  주문
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(item.getPrice() * item.getQuantity());
        order.setStatus("주문 준비중");
        order.setItems(List.of(item));


        //then:재고 부족으로 상품 주문 실패
        assertThrows(IllegalStateException.class, () -> orderService.createOrder(order));

    }


    @Test
    @DisplayName("재고 업데이트 확인")
    public void checkStockTest() {
        //given: 상품 등록
        ProductDto checkStockItem = new ProductDto();
        checkStockItem.setProductName("남성용 후드집업");
        checkStockItem.setPrice(10000);
        checkStockItem.setStock(4);
        checkStockItem.setProductImg("tset11@naver.com");
        checkStockItem.setDescription("테스트 상품입니다.");
        productMapper.insertProduct(checkStockItem);

        OrderItemDto item = new OrderItemDto();
        item.setQuantity(2);
        item.setProductName("남성용 후드집업");
        item.setPrice(10000L);
        item.setProductId(checkStockItem.getId());

        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(item.getPrice() * item.getQuantity());
        order.setStatus("주문 준비중");
        order.setItems(List.of(item));

        Long newOrder = orderService.createOrder(order);


        //then 수량 4 -> 2로
        assertThat(productMapper.readProductById(checkStockItem.getId()).getStock()).isEqualTo(2);

    }


    @Test
    @DisplayName("트랜잭션 롤백 테스트")
    public void rollbackTest() {
        //given: 상품 등록
        ProductDto checkStockItem = new ProductDto();
        checkStockItem.setProductName("남성용 후드집업");
        checkStockItem.setPrice(10000);
        checkStockItem.setStock(4);
        checkStockItem.setProductImg("tset11@naver.com");
        checkStockItem.setDescription("테스트 상품입니다.");
        productMapper.insertProduct(checkStockItem);

        OrderItemDto item = new OrderItemDto();
        item.setQuantity(2);
        item.setProductName("남성용 후드집업");
        item.setPrice(10000L);
        item.setProductId(checkStockItem.getId());

        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(item.getPrice() * item.getQuantity());
        order.setStatus("주문 준비중");
        order.setItems(List.of(item));


        assertThrows(RuntimeException.class, () -> orderService.createOrder(order));
        verificationHelper.assertOrderNotExist(order.getId());
    }


    @Test
    @DisplayName("주문조회")
    public void getOrderDetailsTest(){

        //given 상품 등록
        ProductDto productDto = new ProductDto();
        productDto.setProductName("테스트 상품");
        productDto.setPrice(10000);
        productDto.setStock(10);
        productDto.setProductImg("tset@naver.com");
        productDto.setDescription("테스트 상품입니다.");
        productMapper.insertProduct(productDto);


        //주문 아이템
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setQuantity(2);
        orderItemDto.setProductName("테스트 상품");
        orderItemDto.setPrice(20000L);
        orderItemDto.setProductId(productDto.getId());

        //주문
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(orderItemDto.getPrice() * orderItemDto.getQuantity());
        order.setItems(List.of(orderItemDto));

        // when
        Long orderId = orderService.createOrder(order);


        //then -- 주문 상세 조회
         OrderDto result = orderService.getOrderDetails(orderId);


         //검증
         assertNotNull(result);
        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getTotalPrice(), result.getTotalPrice());

        // 주문상품 검증
        assertNotNull(result.getItems());
        assertEquals(1, result.getItems().size());

        OrderItemDto item = result.getItems().get(0);
        assertEquals(orderItemDto.getProductId(), item.getProductId());
        assertEquals(orderItemDto.getProductName(), item.getProductName());
        assertEquals(orderItemDto.getQuantity(), item.getQuantity());
        assertEquals(orderItemDto.getPrice(), item.getPrice());
    }



    @Test
    @DisplayName("결제 실패후 주문은 커밋, 결제는 롤백")
    void failPayProcessTest(){
        //given 상품 등록
        ProductDto productDto = new ProductDto();
        productDto.setProductName("테스트 상품");
        productDto.setPrice(10000);
        productDto.setStock(10);
        productDto.setProductImg("tset@naver.com");
        productDto.setDescription("테스트 상품입니다.");
        productMapper.insertProduct(productDto);


        //주문 아이템
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setQuantity(2);
        orderItemDto.setProductName("테스트 상품");
        orderItemDto.setPrice(20000L);
        orderItemDto.setProductId(productDto.getId());

        //주문
        OrderDto order = new OrderDto();
        order.setUserId(34L);
        order.setCartId(2L);
        order.setTotalPrice(orderItemDto.getPrice() * orderItemDto.getQuantity());
        order.setItems(List.of(orderItemDto));

        // when
        Long orderId = orderService.createOrder(order);


        //then -- 주문 상세 조회
        OrderDto orderResult = orderService.getOrderDetails(orderId);
        PaymentDto payResult = paymentMapper.selectPaymentLogByOrderId(orderId);

        assertThat(orderResult).isNotNull();
        assertThat(payResult).isNull();

    }

}



