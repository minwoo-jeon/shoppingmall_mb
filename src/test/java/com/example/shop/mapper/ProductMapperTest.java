package com.example.shop.mapper;

import com.example.shop.domain.ProductDto;
import jakarta.validation.constraints.Max;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;
@SpringBootTest
@MapperScan("com.example.shop.mapper") //mapper인터페이스 위치
@Transactional //테서드 메서드 실행후 자동 롤백
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    ProductDto  testProduct;

    @BeforeEach
    void setUp(){
        testProduct = new ProductDto("test제품", 20000, "test", "www.", 5);
        productMapper.insertProduct(testProduct);
    }



//    @Test
//    void insertTest(){
//        ProductDto productDto = new ProductDto("남자 슬랙스",10000,"test 중입니다","sda",5);
//        Long cnt = productMapper.insertProduct(productDto);
//        assertEquals(1,cnt);
//    }


    @Test
    void selectTest(){
        Long productId = testProduct.getId();
        System.out.println("productId = " + productId);
        ProductDto findProduct = productMapper.readProductById(productId);

        assertTrue(findProduct != null, "상품이 존재해야 함");
        String expectName = "test제품";
        assertEquals(expectName,findProduct.getProductName());
    }


    @Test
    void deleteTest(){
//        Long id = 123123L;
        Long productId = testProduct.getId();
        int deleteCnt = productMapper.deleteProduct(productId);
        assertEquals(1, deleteCnt, "삭제된 row 수가 1이어야 함");
        //기대값 , 실제값, message(테스트 실패시 콘솔에 출력될 메세지)


        ProductDto findPr = productMapper.readProductById(productId);
        assertTrue(findPr == null, "삭제 후 상품이 존재하면 안됨");
    }



    @Test
    void updateTest(){
        testProduct.setProductName("updateTest제품");
        testProduct.setPrice(30000);
        testProduct.setDescription("update 테스트 입니다잉");
        testProduct.setProductImg("updatesada");
        testProduct.setStock(10);

        productMapper.updateProduct(testProduct);

        Long id = testProduct.getId();
        ProductDto  updateProduct = productMapper.readProductById(id);
        assertEquals("updateTest제품" , updateProduct.getProductName());

    }

    @Test
    void changeStockTest(){

    }

//    @Test
//    void selectAllTest(){
//        productMapper.deleteAll();
//
//        for (int i = 0; i < 10; i++) {
//            ProductDto productDto = new ProductDto("testzxcz"+ i , 10000 , "test중입니다", "www", 1);
//            productMapper.insertProduct(productDto);
//        }
//
//
//         List<ProductDto> getAllProduct =productMapper.readAllProduct();
//         assertTrue(getAllProduct.size() == 10);
//
//    }



}
