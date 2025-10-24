package com.example.shop.service;

import com.example.shop.domain.ProductDto;
import com.example.shop.mapper.ProductMapper;
import jakarta.validation.constraints.Max;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class ProductService {

    public final ProductMapper productMapper;



    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    //상품 등록
    public Long register(ProductDto productDto) {
        log.info("service.registerProduct() 시작 ");

        //DB 중복체크(상품명 기준)
        ProductDto exist =  productMapper.findByProductName(productDto.getProductName());

        if (exist != null) {
            log.warn("이미 존재하는 상품: name={} ", productDto.getProductName());
            throw new IllegalStateException("이미 존재하는 상품입니다");
        }
            Long id  = productMapper.insertProduct(productDto);
            log.info("상품 등록 완료: name ={} , 생성 ID={}" , productDto.getProductName() , productDto.getId());
            return id;
    }


    //상품 수정
    public ProductDto modify(Long productId , ProductDto productDto) {


        if(! productMapper.existById(productId)){
            throw new IllegalStateException("해당 상품이 존재하지 않습니다. id=" + productId);
        }

         productDto.setId(productId);

        int result = productMapper.updateProduct(productDto);
            
            //실패
            if (result == 0) {
                log.error("상품 수정 실패 - db 반영안됌: id={}" + productId);
                throw new IllegalStateException("상품 수정에 실패했습니다. id={}" + productId);
            }

                ProductDto updated =  productMapper.readProductById(productId);
                log.info("상품 수정 완료: {}" , updated);
                return updated;
    }


    //상품 삭제
    public void remove(Long productId) {
        log.info("service.removeProduct()");

        //상품이 존재하는지 확인
        if(!productMapper.existById(productId)){ //상품이 존재하면 t 없으면 f
            throw new IllegalStateException("해당 상품이 존재하지 않습니다. id=" + productId);
        }
         productMapper.deleteProduct(productId);
    }

    //상품 단건 조회
    public ProductDto getProduct(Long productId){
        return productMapper.readProductById(productId);
    }

    //상품 전체 목록 조회
    public List<ProductDto> getAllProduct(){
        return productMapper.readAllProduct();
    }
}
