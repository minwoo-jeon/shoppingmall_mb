package com.example.shop.controller;

import com.example.shop.domain.ProductDto;
import com.example.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //상품 단건조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {

        ProductDto findPr = productService.getProduct(id);
        if (findPr != null) {
            return new ResponseEntity<>(findPr, HttpStatus.OK); //해당 값이 null 아닐경우 body에는 찾은 ProductDto객체를 반환하고 code->200반환
        } else {
            return new ResponseEntity<>("해당 상품은 찾을수 없습니다", HttpStatus.NOT_FOUND);
        }
    }


    //상품 등록
    @PostMapping()
    public ResponseEntity<?> registerProduct(@RequestBody ProductDto productDto) {
        // info -> 정상흐름
        // warn -> 비정상 흐름
        // error -> 예외발생

       // log.info("registerProduct 진입", "요청 데이터: {}", productDto);     x
       // SLF4J는 첫 번째 인자에 포맷 문자열을 넣고, 뒤에 데이터를 채워 넣는다
        log.info("controller.registerProduct() , 요청 데이터:{}" , productDto );
        try {
            productService.register(productDto);
            log.info("상품 등록 성공:{}", productDto.getProductName());
            return new ResponseEntity<>("상품 등록 성공", HttpStatus.CREATED); //201코드 -> 클라이언트 요청이 성공적으로 처리되었을떄

         }catch (IllegalStateException e) {
                log.warn("상품 등록 실패 이유 : {}", e.getMessage());
                return new ResponseEntity<>(e.getMessage() , HttpStatus.CONFLICT);
                //http.confict - > 클라이언트가 보낸 요청 자체는 문법적으로 맞지만, 409에러코드 반환
                //리소스의 현재 상태와 충돌(conf   lict)이 발생해서 처리할 수 없는 경우에 사용합니다

        } catch (Exception e) {
            log.error("상품 등록 중 예외 발생", e);  //여기서 예외 객체를 넘겨서 스택 트레스이까지 출력해줘야함
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR); // 500-> 클라이언트 요청 자체는 문제가 없지만 서버 오류
        }
    }


    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        try{
              productService.remove(id);
              log.info("상품 삭제완료:{}"+ id);
              return new ResponseEntity<>("상품 삭제 성공", HttpStatus.OK);
        } catch (IllegalStateException e){
             log.warn("상품 삭제 실패 이유:{}" + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("상품 등록 중 예외 발생", e);  //여기서 예외 객체를 넘겨서 스택 트레스이까지 출력해줘야함
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR); // 500-> 클라이언트 요청 자체는 문제가 없지만 서버 오류
        }
    }


    //상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id")Long id , @RequestBody ProductDto productDto) {
        try{
             ProductDto updated = productService.modify(id , productDto);
            return new ResponseEntity<>( updated , HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.info("상품 수정중 오류발생 : {}" + e);
            return new ResponseEntity<>("상품 수정중 오류발생.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}