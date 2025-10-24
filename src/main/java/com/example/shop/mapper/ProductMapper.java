package com.example.shop.mapper;

import com.example.shop.domain.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.*;

@Mapper
public interface ProductMapper {

    //상품 단건 조회(id 기준)
    ProductDto readProductById(Long productId);


    //상품 존재 여부 확인(중복 체크용)
    boolean existById(Long productId);
    //신규 등록 중복 체크 - 상품단건조회(이름 기준)
    ProductDto findByProductName(String productName);

    //제고 차감
    int decreaseStock(@Param("productId") Long productId, @Param("stockNum") int stockNum);

    //재고 수정
    int changeStock(@Param("productId")Long productId,@Param("stockNum") int stockNum);

    Long insertProduct(ProductDto productDto);
    int deleteProduct(Long productId);

    int updateProduct(ProductDto productDto);

    List<ProductDto> readAllProduct();

    void deleteAll();

}
